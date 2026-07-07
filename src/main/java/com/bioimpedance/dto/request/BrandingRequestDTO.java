package com.bioimpedance.dto.request;

import com.bioimpedance.constants.PdfTheme;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandingRequestDTO {

    @Size(max = 100, message = "Texto da marca d'água deve ter no máximo 100 caracteres")
    private String watermarkText;

    @DecimalMin(value = "0.0", message = "Opacidade mínima é 0.0")
    @DecimalMax(value = "1.0", message = "Opacidade máxima é 1.0")
    private Double watermarkOpacity;

    private PdfTheme theme;

    @Size(max = 150, message = "Nome do rodapé deve ter no máximo 150 caracteres")
    private String footerName;

    @Size(max = 200, message = "Contato do rodapé deve ter no máximo 200 caracteres")
    private String footerContact;

    @Size(max = 200, message = "Redes sociais do rodapé devem ter no máximo 200 caracteres")
    private String footerSocial;
}