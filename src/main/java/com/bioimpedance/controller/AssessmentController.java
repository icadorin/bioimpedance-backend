package com.bioimpedance.controller;

import com.bioimpedance.dto.request.*;
import com.bioimpedance.dto.response.AssessmentPanelDTO;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.dto.response.CalculationFlowResponseDTO;
import com.bioimpedance.dto.response.MeasurementSaveResponseDTO;
import com.bioimpedance.pagination.PageResponse;
import com.bioimpedance.service.AssessmentDraftService;
import com.bioimpedance.service.AssessmentFlowService;
import com.bioimpedance.service.AssessmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentDraftService assessmentDraftService;
    private final AssessmentFlowService assessmentFlowService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssessmentResponseDTO create(@Valid @RequestBody AssessmentRequestDTO dto) {
        return assessmentService.create(dto);
    }

    /**
     * Calcula sem efeito colateral (Fase 13 / B4 — DEC-58).
     * Só DRAFT; não grava resultado nem auditoria.
     */
    @PostMapping("/{id}/calculate")
    public CalculationFlowResponseDTO calculate(
        @PathVariable String id,
        @Valid @RequestBody CalculateRequestDTO dto
    ) {
        return assessmentFlowService.calculate(id, dto);
    }
    // ── Fluxo de rascunho (Fase 13 / B3 — DEC-52, doc.md §17.8) ──

    @PostMapping("/draft")
    @ResponseStatus(HttpStatus.CREATED)
    public AssessmentPanelDTO createDraft(@Valid @RequestBody CreateDraftRequestDTO dto) {
        return assessmentDraftService.createDraft(dto);
    }

    @PatchMapping("/{id}/context")
    public AssessmentPanelDTO updateContext(
        @PathVariable String id,
        @Valid @RequestBody UpdateContextRequestDTO dto
    ) {
        return assessmentDraftService.updateContext(id, dto);
    }

    @PutMapping("/{id}/measurements/{inputId}")
    public MeasurementSaveResponseDTO upsertMeasurement(
        @PathVariable String id,
        @PathVariable String inputId,
        @RequestBody UpsertMeasurementRequestDTO dto
    ) {
        return assessmentDraftService.upsertMeasurement(id, inputId, dto);
    }

    @GetMapping("/{id}/panel")
    public AssessmentPanelDTO getPanel(@PathVariable String id) {
        return assessmentDraftService.getPanel(id);
    }

    @GetMapping("/client/{clientId}")
    public List<AssessmentResponseDTO> findByClient(@PathVariable String clientId) {
        return assessmentService.findByClientId(clientId);
    }

    @GetMapping
    public PageResponse<AssessmentResponseDTO> findPaged(@Valid @ModelAttribute AssessmentFilter filter) {
        return assessmentService.findPaged(filter);
    }

    @GetMapping("/{id}")
    public AssessmentResponseDTO findById(@PathVariable String id) {
        return assessmentService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        assessmentService.delete(id);
    }
}