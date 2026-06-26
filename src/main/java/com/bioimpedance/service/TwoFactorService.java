package com.bioimpedance.service;

import com.bioimpedance.dto.auth.*;
import com.bioimpedance.entity.TwoFactorTempToken;
import com.bioimpedance.entity.User;
import com.bioimpedance.repository.TwoFactorTempTokenRepository;
import com.bioimpedance.repository.UserRepository;
import com.bioimpedance.util.CookieUtil;
import com.bioimpedance.util.CsrfTokenUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TwoFactorService {

    private final UserRepository userRepository;
    private final TwoFactorTempTokenRepository tempTokenRepository;
    private final TwoFactorEncryptionService encryptionService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CurrentUserService currentUserService;
    private final CookieUtil cookieUtil;
    // CsrfTokenUtil é um @Component simples sem dependências — sem risco de ciclo
    private final CsrfTokenUtil csrfTokenUtil;

    private static final String ISSUER = "Bioimpedance";
    private static final int BACKUP_CODES_COUNT = 10;
    private static final int BACKUP_CODE_LENGTH = 8;
    private static final int MAX_ATTEMPTS = 3;
    private static final Duration TEMP_TOKEN_DURATION = Duration.ofMinutes(5);

    private final SecureRandom secureRandom = new SecureRandom();

    // ==================== SETUP 2FA ====================

    @Transactional
    public TwoFactorSetupResponseDTO initiateSetup() {
        User user = currentUserService.getCurrentUser();
        if (user.isTwoFactorEnabled()) {
            throw new IllegalArgumentException("2FA já está ativo");
        }

        byte[] secretBytes = new byte[20];
        secureRandom.nextBytes(secretBytes);
        String secret = new Base32().encodeToString(secretBytes).replace("=", "");
        String encryptedSecret = encryptionService.encrypt(secret);

        user.setTwoFactorTempSecret(encryptedSecret);
        userRepository.saveAndFlush(user);

        String qrCodeUrl = generateQrCodeUrl(user.getEmail(), secret);
        String qrCodeBase64 = generateQrCodeImage(qrCodeUrl);

        List<String> backupCodesPlain = generateBackupCodes();
        List<String> backupCodesHashed = backupCodesPlain.stream()
            .map(passwordEncoder::encode)
            .toList();

        user.setTwoFactorBackupCodes(String.join(",", backupCodesHashed));
        userRepository.save(user);

        return TwoFactorSetupResponseDTO.builder()
            .secret(secret)
            .qrCodeUrl(qrCodeBase64)
            .backupCodes(backupCodesPlain)
            .build();
    }

    @Transactional
    public void confirmSetup(TwoFactorConfirmRequestDTO dto) {
        User user = currentUserService.getCurrentUser();
        if (user.isTwoFactorEnabled()) {
            throw new IllegalArgumentException("2FA já está ativo");
        }
        if (user.getTwoFactorTempSecret() == null) {
            throw new IllegalArgumentException("Setup de 2FA não iniciado");
        }

        String secret = encryptionService.decrypt(user.getTwoFactorTempSecret());
        if (!validateTotpCode(secret, dto.getCode().trim())) {
            throw new IllegalArgumentException("Código TOTP inválido");
        }

        user.setTwoFactorSecret(user.getTwoFactorTempSecret());
        user.setTwoFactorTempSecret(null);
        user.setTwoFactorEnabled(true);
        user.setTwoFactorSetupAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        log.info("2FA ativado para userId={}", user.getId());
    }

    // ==================== DESATIVAR 2FA ====================

    @Transactional
    public void disableTwoFactor(TwoFactorDisableRequestDTO dto) {
        User user = currentUserService.getCurrentUser();
        if (!user.isTwoFactorEnabled()) {
            throw new IllegalArgumentException("2FA não está ativo");
        }

        String secret = encryptionService.decrypt(user.getTwoFactorSecret());
        if (!validateTotpCode(secret, dto.getCode())) {
            throw new IllegalArgumentException("Código TOTP inválido");
        }

        user.setTwoFactorEnabled(false);
        user.setTwoFactorSecret(null);
        user.setTwoFactorTempSecret(null);
        user.setTwoFactorBackupCodes(null);
        user.setTwoFactorSetupAt(null);
        userRepository.save(user);

        log.info("2FA desativado para userId={}", user.getId());
    }

    // ==================== LOGIN COM 2FA ====================

    public boolean requiresTwoFactor(String email) {
        return userRepository.findByEmail(email)
            .map(User::isTwoFactorEnabled)
            .orElse(false);
    }

    @Transactional
    public String generateTempToken(String userId, boolean rememberMe) {
        tempTokenRepository.deleteByUserId(userId);

        String tokenId = UUID.randomUUID().toString();
        String token = jwtService.generateTwoFactorTempToken(userId, tokenId);
        String tokenHash = hashToken(token);

        TwoFactorTempToken tempToken = TwoFactorTempToken.builder()
            .id(tokenId)
            .userId(userId)
            .tokenHash(tokenHash)
            .expiresAt(Instant.now().plus(TEMP_TOKEN_DURATION))
            .createdAt(Instant.now())
            .used(false)
            .attempts(0)
            .blocked(false)
            .rememberMe(rememberMe)
            .build();
        tempTokenRepository.save(tempToken);

        return token;
    }

    @Transactional(noRollbackFor = {IllegalArgumentException.class, SecurityException.class})
    public TwoFactorLoginResponseDTO verifyLoginCode(TwoFactorVerifyRequestDTO dto,
                                                     HttpServletResponse response) {
        TwoFactorTempToken tempToken = validateTempToken(dto.getTempToken());

        User user = userRepository.findById(tempToken.getUserId())
            .orElseThrow(() -> new SecurityException("Usuário não encontrado"));

        if (!user.isTwoFactorEnabled() || user.getTwoFactorSecret() == null) {
            throw new SecurityException("2FA não configurado para este usuário");
        }

        String secret = encryptionService.decrypt(user.getTwoFactorSecret());
        String code = dto.getCode().trim();

        boolean valid = validateTotpCode(secret, code);
        if (!valid) {
            valid = validateBackupCode(user, code);
        }

        if (!valid) {
            tempToken.setAttempts(tempToken.getAttempts() + 1);
            if (tempToken.getAttempts() >= MAX_ATTEMPTS) {
                tempToken.setBlocked(true);
                tempTokenRepository.save(tempToken);
                throw new SecurityException("Muitas tentativas. Token bloqueado.");
            }
            tempTokenRepository.save(tempToken);
            throw new IllegalArgumentException("Código inválido. Tentativas restantes: "
                + (MAX_ATTEMPTS - tempToken.getAttempts()));
        }

        tempToken.setUsed(true);
        tempTokenRepository.save(tempToken);

        boolean effectiveRememberMe = tempToken.isRememberMe();

        String tokenFamily = UUID.randomUUID().toString();
        String accessToken = jwtService.generateToken(user.getEmail(), tokenFamily, true);
        String refreshToken = jwtService.generateRefreshToken(
            user.getEmail(), tokenFamily, effectiveRememberMe, true);

        refreshTokenService.createRefreshToken(user.getId(), refreshToken, effectiveRememberMe);

        cookieUtil.setAccessToken(response, accessToken);
        cookieUtil.setRefreshToken(response, refreshToken, effectiveRememberMe);
        // Usa CsrfTokenUtil diretamente — sem depender de AuthService
        cookieUtil.setCsrfToken(response, csrfTokenUtil.generate());

        return TwoFactorLoginResponseDTO.builder()
            .name(user.getName())
            .email(user.getEmail())
            .plan(user.getPlan().getSlug())
            .build();
    }

    // ==================== MÉTODOS PRIVADOS ====================

    private boolean validateTotpCode(String secret, String code) {
        try {
            Base32 base32 = new Base32();
            byte[] secretBytes = base32.decode(secret);
            long timeIndex = System.currentTimeMillis() / 1000 / 30;

            for (int i = 0; i <= 1; i++) {
                long currentTimeIndex = timeIndex + i;
                byte[] timeBytes = new byte[8];
                long temp = currentTimeIndex;
                for (int j = 7; j >= 0; j--) {
                    timeBytes[j] = (byte) (temp & 0xFF);
                    temp >>= 8;
                }

                Mac mac = Mac.getInstance("HmacSHA1");
                mac.init(new SecretKeySpec(secretBytes, "HmacSHA1"));
                byte[] hash = mac.doFinal(timeBytes);

                int offset = hash[hash.length - 1] & 0xF;
                int binary = ((hash[offset] & 0x7F) << 24)
                    | ((hash[offset + 1] & 0xFF) << 16)
                    | ((hash[offset + 2] & 0xFF) << 8)
                    | (hash[offset + 3] & 0xFF);
                int otp = binary % 1_000_000;

                if (code.equals(String.format("%06d", otp))) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("Erro ao validar TOTP", e);
            return false;
        }
    }

    private boolean validateBackupCode(User user, String code) {
        if (user.getTwoFactorBackupCodes() == null) return false;

        String[] hashes = user.getTwoFactorBackupCodes().split(",");
        for (String hash : hashes) {
            if (passwordEncoder.matches(code, hash)) {
                String updatedCodes = Arrays.stream(hashes)
                    .filter(h -> !h.equals(hash))
                    .collect(Collectors.joining(","));
                user.setTwoFactorBackupCodes(updatedCodes.isEmpty() ? null : updatedCodes);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    private TwoFactorTempToken validateTempToken(String token) {
        String tokenHash = hashToken(token);
        TwoFactorTempToken tempToken = tempTokenRepository.findByTokenHash(tokenHash)
            .orElseThrow(() -> new SecurityException("Token temporário inválido"));

        if (tempToken.isUsed()) throw new SecurityException("Token já utilizado");
        if (tempToken.isBlocked()) throw new SecurityException("Token bloqueado por muitas tentativas");
        if (tempToken.getExpiresAt().isBefore(Instant.now())) throw new SecurityException("Token expirado");
        if (!jwtService.isTwoFactorTempTokenValid(token)) throw new SecurityException("Token inválido");

        return tempToken;
    }

    private String generateQrCodeUrl(String email, String secret) {
        return String.format(
            "otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
            ISSUER, email, secret, ISSUER
        );
    }

    private String generateQrCodeImage(String content) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);
            return "data:image/png;base64,"
                + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code", e);
        }
    }

    private List<String> generateBackupCodes() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < BACKUP_CODES_COUNT; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < BACKUP_CODE_LENGTH; j++) {
                sb.append(secureRandom.nextInt(10));
            }
            codes.add(sb.toString());
        }
        return codes;
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao hash token", e);
        }
    }
}