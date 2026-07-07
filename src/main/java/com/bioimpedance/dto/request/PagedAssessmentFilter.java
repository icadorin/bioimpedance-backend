package com.bioimpedance.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PagedAssessmentFilter {

    @Min(value = 0, message = "page deve ser >= 0")
    private int page = 0;

    @Min(value = 1,   message = "size deve ser >= 1")
    @Max(value = 100, message = "size deve ser <= 100")
    private int size = 20;

    private String clientId;

    /**
     * Recebido como String para evitar erro de conversão do Spring MVC.
     * Validado e convertido para AssessmentMethod no serviço.
     * Valores válidos: navy, bioimpedance, skinfold, imc
     */
    private String method;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @PastOrPresent(message = "from não pode ser data futura")
    private LocalDate from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
}