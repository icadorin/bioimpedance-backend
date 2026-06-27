package com.bioimpedance.controller;

import com.bioimpedance.dto.request.AssessmentRequestDTO;
import com.bioimpedance.dto.request.CalculateRequestDTO;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.dto.response.CalculationResultDTO;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssessmentResponseDTO create(@Valid @RequestBody AssessmentRequestDTO dto) {
        return assessmentService.create(dto);
    }

    @PostMapping("/calculate")
    public CalculationResultDTO calculate(@Valid @RequestBody CalculateRequestDTO dto) {
        return assessmentService.calculate(dto);
    }

    @GetMapping("/client/{clientId}")
    public List<AssessmentResponseDTO> findByClient(@PathVariable String clientId) {
        return assessmentService.findByClientId(clientId);
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
