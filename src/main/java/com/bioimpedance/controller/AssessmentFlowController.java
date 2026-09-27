package com.bioimpedance.controller;

import com.bioimpedance.dto.request.AssessmentFlowRequestDTO;
import com.bioimpedance.dto.response.CalculationFlowResponseDTO;
import com.bioimpedance.service.AssessmentFlowService;
import com.bioimpedance.service.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fluxo novo da avaliação (Fase 12 / Chunk 3).
 * <p>
 * Coexiste com o AssessmentController legacy até o Chunk 4 (DEC-40).
 * O userId vem do SecurityContext AQUI — o service não conhece segurança.
 */
@RestController
@RequestMapping("/api/assessments/flow")
@RequiredArgsConstructor
public class AssessmentFlowController {

    private final AssessmentFlowService assessmentFlowService;
    private final CurrentUserService currentUserService;

    /** doc.md §25: cálculo é ação explícita do profissional. */
    @PostMapping("/calculate")
    public CalculationFlowResponseDTO calculate(@Valid @RequestBody AssessmentFlowRequestDTO dto) {
        return assessmentFlowService.calculate(currentUserService.getCurrentUserId(), dto);
    }
}