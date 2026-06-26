package com.bioimpedance.service;

import com.bioimpedance.constants.Plan;
import com.bioimpedance.dto.auth.AuthResponseDTO;
import com.bioimpedance.dto.auth.LoginRequestDTO;
import com.bioimpedance.dto.auth.RegisterRequestDTO;
import com.bioimpedance.dto.auth.UpdateProfileRequestDTO;
import com.bioimpedance.entity.User;
import com.bioimpedance.exception.TwoFactorRequiredException;
import com.bioimpedance.repository.UserRepository;
import com.bioimpedance.util.CsrfTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final TwoFactorService twoFactorService;
    private final CurrentUserService currentUserService;
    private final CsrfTokenUtil csrfTokenUtil;

    public String generateCsrfToken() {
        return csrfTokenUtil.generate();
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        validatePassword(request.getPassword());

        String emailLower = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(emailLower)) {
            throw new IllegalArgumentException("Este email já está em uso");
        }

        User user = User.builder()
            .email(emailLower)
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName().trim())
            .active(true)
            .plan(Plan.BASIC)
            .build();
        user = userRepository.save(user);

        String tokenFamily = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user.getEmail(), tokenFamily, false);
        String refreshToken = jwtService.generateRefreshToken(
            user.getEmail(), tokenFamily, false, false
        );
        refreshTokenService.createRefreshToken(user.getId(), refreshToken, false);

        return new AuthResponseDTO(accessToken, refreshToken,
            user.getName(), user.getEmail(), user.getPlan().getSlug(), false);
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
            .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        if (!user.isActive()) {
            throw new IllegalArgumentException("Conta desativada");
        }

        if (twoFactorService.requiresTwoFactor(user.getEmail())) {
            String tempToken = twoFactorService.generateTempToken(
                user.getId(), request.isRememberMe());
            throw new TwoFactorRequiredException(tempToken, user.getEmail());
        }

        boolean effectiveRememberMe = request.isRememberMe();
        String tokenFamily = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user.getEmail(), tokenFamily, true);
        String refreshToken = jwtService.generateRefreshToken(
            user.getEmail(), tokenFamily, effectiveRememberMe, true);
        refreshTokenService.createRefreshToken(user.getId(), refreshToken, effectiveRememberMe);

        return new AuthResponseDTO(accessToken, refreshToken,
            user.getName(), user.getEmail(), user.getPlan().getSlug(), effectiveRememberMe);
    }

    @Transactional
    public AuthResponseDTO refresh(String oldRefreshToken) {
        if (!jwtService.isRefreshTokenValid(oldRefreshToken)) {
            throw new SecurityException("Refresh token inválido");
        }

        String email = jwtService.extractEmail(oldRefreshToken);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new SecurityException("Usuário não encontrado"));

        boolean rememberMe = jwtService.extractRememberMe(oldRefreshToken);
        boolean twoFactorVerified = jwtService.extractTwoFactorVerified(oldRefreshToken);

        refreshTokenService.rotateRefreshToken(oldRefreshToken)
            .orElseThrow(() -> new SecurityException(
                "Refresh token inválido ou já utilizado. Faça login novamente."));

        String tokenFamily = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user.getEmail(), tokenFamily, twoFactorVerified);
        String newRefreshToken = jwtService.generateRefreshToken(
            user.getEmail(), tokenFamily, rememberMe, twoFactorVerified);
        refreshTokenService.createRefreshToken(user.getId(), newRefreshToken, rememberMe);

        return new AuthResponseDTO(accessToken, newRefreshToken,
            user.getName(), user.getEmail(), user.getPlan().getSlug(), rememberMe);
    }

    @Transactional
    public void updateProfile(UpdateProfileRequestDTO dto) {
        User user = currentUserService.getCurrentUser();
        String emailLower = dto.getEmail().trim().toLowerCase();

        if (!emailLower.equals(user.getEmail()) && userRepository.existsByEmail(emailLower)) {
            throw new IllegalArgumentException("Este email já está em uso");
        }

        user.setName(dto.getName().trim());
        user.setEmail(emailLower);
        userRepository.save(user);
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8)
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres");
        if (!password.matches(".*[A-Z].*"))
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra maiúscula");
        if (!password.matches(".*[0-9].*"))
            throw new IllegalArgumentException("A senha deve conter pelo menos um número");
    }
}