package com.bioimpedance.dto.response;

import com.bioimpedance.constants.PdfTheme;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandingResponseDTO {

    private String logoUrl;
    private String watermarkText;
    private Double watermarkOpacity;
    private PdfTheme theme;
    private String footerName;
    private String footerContact;
    private String footerSocial;

    /** Indica se o usuário já tem um logo configurado. */
    private boolean hasLogo;
}