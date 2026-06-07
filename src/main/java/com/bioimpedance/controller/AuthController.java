package com.bioimpedance.controller;

import com.bioimpedance.dto.auth.*;
import com.bioimpedance.entity.User;
import com.bioimpedance.exception.TwoFactorRequiredException;
import com.bioimpedance.repository.RefreshTokenRepository;
import com.bioimpedance.repository.UserRepository;
import com.bioimpedance.service.AuthService;
import com.bioimpedance.service.JwtService;
import com.bioimpedance.service.TwoFactorService;
import com.bioimpedance.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TwoFactorService twoFactorService;
    private final CookieUtil cookieUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request,
                                      HttpServletResponse response) {
        AuthResponseDTO auth = authService.register(request);
        setAuthCookies(response, auth);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("name", auth.getName(),
                "email", auth.getEmail(),
                "plan", auth.getPlan()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO request,
                                   HttpServletResponse response) {
        try {
            AuthResponseDTO auth = authService.login(request);
            setAuthCookies(response, auth);
            return ResponseEntity.ok(Map.of(
                "name", auth.getName(),
                "email", auth.getEmail(),
                "plan", auth.getPlan(),
                "requires2FA", false
            ));
        } catch (TwoFactorRequiredException e) {
            return ResponseEntity.ok(Map.of(
                "requires2FA", true,
                "tempToken", e.getTempToken(),
                "email", e.getEmail()
            ));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(
        @Valid @RequestBody UpdateProfileRequestDTO dto) {
        authService.updateProfile(dto);
        return ResponseEntity.ok().build();
    }

    // ==================== ENDPOINTS 2FA ====================

    @PostMapping("/2fa/setup")
    public ResponseEntity<TwoFactorSetupResponseDTO> initiateTwoFactorSetup() {
        return ResponseEntity.ok(twoFactorService.initiateSetup());
    }

    @PostMapping("/2fa/confirm")
    public ResponseEntity<Void> confirmTwoFactorSetup(@Valid @RequestBody TwoFactorConfirmRequestDTO dto) {
        twoFactorService.confirmSetup(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/2fa/disable")
    public ResponseEntity<Void> disableTwoFactor(@Valid @RequestBody TwoFactorDisableRequestDTO dto) {
        twoFactorService.disableTwoFactor(dto);
        return ResponseEntity.ok().build();
    }

    /**
     * CORREÇÃO: Não expõe token/refreshToken no body.
     * Os cookies HttpOnly já foram setados pelo TwoFactorService.verifyLoginCode().
     * O frontend usa apenas os dados do usuário para atualizar o estado.
     */
    @PostMapping("/2fa/verify")
    public ResponseEntity<?> verifyTwoFactorLogin(@Valid @RequestBody TwoFactorVerifyRequestDTO dto,
                                                  HttpServletResponse response) {
        TwoFactorLoginResponseDTO result = twoFactorService.verifyLoginCode(dto, response);
        return ResponseEntity.ok(Map.of(
            "name", result.getName(),
            "email", result.getEmail(),
            "plan", result.getPlan()
        ));
    }

    // ==================== REFRESH / LOGOUT / ME ====================

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request,
                                     HttpServletResponse response) {

        String refreshToken = cookieUtil.getRefreshToken(request)
            .orElseThrow(() -> new IllegalArgumentException("Refresh token não encontrado"));

        AuthResponseDTO auth = authService.refresh(refreshToken);
        setAuthCookies(response, auth);

        return ResponseEntity.ok(Map.of("refreshed", true));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                       HttpServletResponse response) {

        String token = cookieUtil.getAccessToken(request).orElse(null);

        if (token != null && jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);
            userRepository.findByEmail(email).ifPresent(user ->
                refreshTokenRepository.deleteByUserId(user.getId())
            );
        }

        cookieUtil.clearCookies(response);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        String token = cookieUtil.getAccessToken(request).orElse(null);

        if (token == null || !jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = jwtService.extractEmail(token);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return ResponseEntity.ok(Map.of(
            "name", user.getName(),
            "email", user.getEmail(),
            "plan", user.getPlan().getSlug(),
            "twoFactorEnabled", user.isTwoFactorEnabled()
        ));
    }

    private void setAuthCookies(HttpServletResponse response, AuthResponseDTO auth) {
        cookieUtil.setAccessToken(response, auth.getToken());
        cookieUtil.setRefreshToken(response, auth.getRefreshToken(), auth.isRememberMe());
        cookieUtil.setCsrfToken(response, authService.generateCsrfToken());
    }
}
