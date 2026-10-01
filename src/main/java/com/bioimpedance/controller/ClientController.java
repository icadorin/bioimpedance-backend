package com.bioimpedance.controller;

import com.bioimpedance.dto.request.ClientFilter;
import com.bioimpedance.dto.request.ClientRequestDTO;
import com.bioimpedance.dto.request.ProgressFilter;
import com.bioimpedance.dto.response.ClientProgressDTO;
import com.bioimpedance.dto.response.ClientResponseDTO;
import com.bioimpedance.dto.response.PreviousMeasurementDTO;
import com.bioimpedance.pagination.PageResponse;
import com.bioimpedance.service.AssessmentService;
import com.bioimpedance.service.ClientProgressService;
import com.bioimpedance.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientProgressService clientProgressService;
    private final AssessmentService assessmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO create(@Valid @RequestBody ClientRequestDTO dto) {
        return clientService.create(dto);
    }

    /**
     * Listagem paginada com filtros.
     * Ex: GET /api/clients?page=0&size=20&search=joao&status=ACTIVE&sort=name&direction=asc
     */
    @GetMapping
    public PageResponse<ClientResponseDTO> findAllPaged(@Valid @ModelAttribute ClientFilter filter) {
        return clientService.findPaged(filter);
    }

    /**
     * Endpoint RESTful para listagem paginada de progresso dos alunos.
     * Ex: GET /api/clients/progress?page=0&size=20&sort=bodyFatDiff&direction=desc
     */
    @GetMapping("/progress")
    public PageResponse<ClientProgressDTO> getClientProgressPaged(
        @Valid @ModelAttribute ProgressFilter filter) {
        return clientProgressService.getPagedProgress(filter);
    }

    @GetMapping("/search")
    public List<ClientResponseDTO> search(@RequestParam(defaultValue = "") String q) {
        return clientService.search(q);
    }

    @GetMapping("/{id}")
    public ClientResponseDTO findById(@PathVariable String id) {
        return clientService.findById(id);
    }

    /**
     * Última medida por input em avaliações anteriores (DEC-57, doc.md §17.8).
     * excludeAssessmentId é opcional — o B3 usa para excluir o rascunho atual.
     */
    @GetMapping("/{id}/previous-measurements")
    public Map<String, PreviousMeasurementDTO> getPreviousMeasurements(
        @PathVariable String id,
        @RequestParam(required = false) String excludeAssessmentId) {
        return assessmentService.getPreviousMeasurements(id, excludeAssessmentId);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO update(@PathVariable String id,
                                    @Valid @RequestBody ClientRequestDTO dto) {
        return clientService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        clientService.delete(id);
    }
}