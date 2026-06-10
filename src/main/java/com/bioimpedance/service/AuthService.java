package com.bioimpedance.service;

import com.bioimpedance.constants.Plan;
import com.bioimpedance.dto.auth.AuthResponseDTO;
import com.bioimpedance.dto.auth.LoginRequestDTO;
import com.bioimpedance.dto.auth.RegisterRequestDTO;
import com.bioimpedance.dto.auth.UpdateProfileRequestDTO;
import com.bioimpedance.entity.User;
import com.bioimpedance.exception.TwoFactorRequiredException;
import com.bioimpedance.repository.SessionFingerprintRepository;
import com.bioimpedance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final SessionFingerprintRepository fingerprintRepository;
    private final TwoFactorService twoFactorService;
    private final CurrentUserService currentUserService;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateCsrfToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        validatePassword(request.getPassword());

        String emailLower = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(emailLower)) {
            throw new IllegalArgumentException("Este email já está em uso");
        }

        String name = request.getName().trim();

        User user = User.builder()
            .email(emailLower)
            .password(passwordEncoder.encode(request.getPassword()))
            .name(name)
            .active(true)
            .plan(Plan.BASIC)
            .build();

        user = userRepository.save(user);

        String tokenFamily = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user.getEmail(), tokenFamily);

        // CORREÇÃO: Usuário novo não tem 2FA, então rememberMe é false (7 dias)
        boolean rememberMe = false;
        String refreshToken = jwtService.generateRefreshToken(user.getEmail(), tokenFamily, rememberMe);

        refreshTokenService.createRefreshToken(user.getId(), refreshToken, rememberMe);

        return new AuthResponseDTO(accessToken, refreshToken,
            user.getName(), user.getEmail(), user.getPlan().getSlug(), rememberMe);
    }

    /**
     * Login com suporte a 2FA.
     * Se 2FA estiver ativo, retorna null nos tokens e dispara exceção de controle
     * para o controller enviar requires2FA + tempToken.
     */
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
            // Se tem 2FA, passa o rememberMe do request para o tempToken
            // (O TwoFactorService vai reavaliar a segurança na hora de gerar o token final)
            String tempToken = twoFactorService.generateTempToken(
                user.getId(), request.isRememberMe());
            throw new TwoFactorRequiredException(tempToken, user.getEmail());
        }

        // REGRA DE SEGURANÇA: Se não tem 2FA, ignora o "Lembrar-me" do frontend
        boolean effectiveRememberMe = request.isRememberMe() && user.isTwoFactorEnabled();

        String tokenFamily = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user.getEmail(), tokenFamily);
        String refreshToken = jwtService.generateRefreshToken(
            user.getEmail(), tokenFamily, effectiveRememberMe); // 3 argumentos

        refreshTokenService.createRefreshToken(user.getId(), refreshToken, effectiveRememberMe); // 3 argumentos

        return new AuthResponseDTO(accessToken, refreshToken,
            user.getName(), user.getEmail(), user.getPlan().getSlug(), effectiveRememberMe);
    }

    @Transactional
    public AuthResponseDTO refresh(String oldRefreshToken) {
        try {
            if (!jwtService.isRefreshTokenValid(oldRefreshToken)) {
                throw new SecurityException("Refresh token inválido");
            }

            String email = jwtService.extractEmail(oldRefreshToken);
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SecurityException("Usuário não encontrado"));

            // Extrai o rememberMe do token antigo para manter a mesma duração
            boolean rememberMe = jwtService.extractRememberMe(oldRefreshToken);

            String oldTokenFamily = jwtService.extractTokenFamily(oldRefreshToken);
            String newTokenFamily = UUID.randomUUID().toString(); // Rotation de família (Token Rotation)

            String accessToken = jwtService.generateToken(user.getEmail(), newTokenFamily);
            String newRefreshToken = jwtService.generateRefreshToken(
                user.getEmail(), newTokenFamily, rememberMe); // 3 argumentos

            // Atualiza no banco (o método rotateRefreshToken do Service já invalida o antigo)
            refreshTokenService.rotateRefreshToken(oldRefreshToken);
            refreshTokenService.createRefreshToken(user.getId(), newRefreshToken, rememberMe); // 3 argumentos

            return new AuthResponseDTO(accessToken, newRefreshToken,
                user.getName(), user.getEmail(), user.getPlan().getSlug(), rememberMe);

        } catch (Exception e) {
            throw new SecurityException("Falha ao renovar sessão: " + e.getMessage());
        }
    }

    @Transactional
    public void updateProfile(UpdateProfileRequestDTO dto) {
        User user = currentUserService.getCurrentUser();
        String emailLower = dto.getEmail().trim().toLowerCase();

        if (!emailLower.equals(user.getEmail())
            && userRepository.existsByEmail(emailLower)) {
            throw new IllegalArgumentException("Este email já está em uso");
        }

        user.setName(dto.getName().trim());
        user.setEmail(emailLower);
        userRepository.save(user);
    }

    private void blockAllSessions(String email) {
        userRepository.findByEmail(email).ifPresent(user ->
            fingerprintRepository.findByUserIdOrderByLastUsedAtDesc(user.getId())
                .forEach(s -> {
                    s.setBlocked(true);
                    fingerprintRepository.save(s);
                })
        );
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
