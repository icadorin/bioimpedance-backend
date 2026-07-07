package com.bioimpedance.service;

import com.bioimpedance.constants.PlanFeature;
import com.bioimpedance.dto.request.BrandingRequestDTO;
import com.bioimpedance.dto.response.BrandingResponseDTO;
import com.bioimpedance.entity.BrandingProfile;
import com.bioimpedance.repository.BrandingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandingService {

    private static final long MAX_LOGO_SIZE_BYTES = 2 * 1024 * 1024; // 2 MB
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
        "image/png",
        "image/jpeg",
        "image/webp"
    );

    private final BrandingRepository brandingRepository;
    private final BillingService billingService;
    private final CurrentUserService currentUserService;

    // ── GET branding ──────────────────────────────────────────────────────

    public BrandingResponseDTO getBranding() {
        billingService.requireFeature(PlanFeature.CUSTOM_BRANDING);
        String userId = currentUserService.getCurrentUserId();

        BrandingProfile profile = findOrCreate(userId);
        return toResponse(profile);
    }

    // ── GET logo (serve a imagem) ─────────────────────────────────────────

    public ResponseEntity<byte[]> getLogo() {
        billingService.requireFeature(PlanFeature.CUSTOM_BRANDING);
        String userId = currentUserService.getCurrentUserId();

        BrandingProfile profile = brandingRepository.findByUserId(userId).orElse(null);

        if (profile == null || profile.getLogoData() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(profile.getLogoContentType()));
        headers.setContentLength(profile.getLogoData().length);
        // Cache de 1 hora (logo raramente muda)
        headers.setCacheControl("public, max-age=3600");

        return ResponseEntity.ok()
            .headers(headers)
            .body(profile.getLogoData());
    }

    // ── PUT (textos/tema) ─────────────────────────────────────────────────

    @Transactional
    public BrandingResponseDTO updateBranding(BrandingRequestDTO dto) {
        billingService.requireFeature(PlanFeature.CUSTOM_BRANDING);
        String userId = currentUserService.getCurrentUserId();

        BrandingProfile profile = findOrCreate(userId);

        // Atualiza apenas os campos presentes no DTO (null = não altera)
        if (dto.getWatermarkText() != null) {
            profile.setWatermarkText(dto.getWatermarkText().isBlank() ? null : dto.getWatermarkText().trim());
        }
        if (dto.getWatermarkOpacity() != null) {
            profile.setWatermarkOpacity(dto.getWatermarkOpacity());
        }
        if (dto.getTheme() != null) {
            profile.setTheme(dto.getTheme());
        }
        if (dto.getFooterName() != null) {
            profile.setFooterName(dto.getFooterName().isBlank() ? null : dto.getFooterName().trim());
        }
        if (dto.getFooterContact() != null) {
            profile.setFooterContact(dto.getFooterContact().isBlank() ? null : dto.getFooterContact().trim());
        }
        if (dto.getFooterSocial() != null) {
            profile.setFooterSocial(dto.getFooterSocial().isBlank() ? null : dto.getFooterSocial().trim());
        }

        return toResponse(brandingRepository.save(profile));
    }

    // ── POST /logo ────────────────────────────────────────────────────────

    @Transactional
    public BrandingResponseDTO uploadLogo(MultipartFile file) {
        billingService.requireFeature(PlanFeature.CUSTOM_BRANDING);
        String userId = currentUserService.getCurrentUserId();

        validateLogoFile(file);

        BrandingProfile profile = findOrCreate(userId);

        try {
            byte[] logoData = file.getBytes();

            profile.setLogoData(logoData);
            profile.setLogoContentType(file.getContentType());
            profile.setLogoSize((long) logoData.length);

            log.debug("Logo salvo no banco para userId={}, tamanho={} bytes", userId, logoData.length);
            return toResponse(brandingRepository.save(profile));

        } catch (Exception e) {
            log.error("Erro ao processar arquivo de logo para userId={}", userId, e);
            throw new RuntimeException("Erro ao processar arquivo de logo");
        }
    }

    // ── DELETE /logo ──────────────────────────────────────────────────────

    @Transactional
    public BrandingResponseDTO deleteLogo() {
        billingService.requireFeature(PlanFeature.CUSTOM_BRANDING);
        String userId = currentUserService.getCurrentUserId();

        BrandingProfile profile = findOrCreate(userId);

        if (profile.getLogoData() != null) {
            profile.setLogoData(null);
            profile.setLogoContentType(null);
            profile.setLogoSize(null);
            brandingRepository.save(profile);
            log.debug("Logo removido do banco para userId={}", userId);
        }

        return toResponse(profile);
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    private BrandingProfile findOrCreate(String userId) {
        return brandingRepository.findByUserId(userId)
            .orElseGet(() -> brandingRepository.save(
                BrandingProfile.builder()
                    .userId(userId)
                    .build()
            ));
    }

    private void validateLogoFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo de logo não pode ser vazio");
        }

        if (file.getSize() > MAX_LOGO_SIZE_BYTES) {
            throw new IllegalArgumentException("Logo deve ter no máximo 2 MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                "Formato de imagem inválido. Use PNG, JPEG ou WebP"
            );
        }
    }

    private BrandingResponseDTO toResponse(BrandingProfile profile) {
        return BrandingResponseDTO.builder()
            .logoUrl(profile.getLogoData() != null ? "/api/branding/logo" : null)
            .watermarkText(profile.getWatermarkText())
            .watermarkOpacity(profile.getWatermarkOpacity())
            .theme(profile.getTheme())
            .footerName(profile.getFooterName())
            .footerContact(profile.getFooterContact())
            .footerSocial(profile.getFooterSocial())
            .hasLogo(profile.getLogoData() != null)
            .build();
    }
}