package com.bioimpedance.service;

import com.bioimpedance.constants.AssessmentMethod;
import com.bioimpedance.constants.PlanFeature;
import com.bioimpedance.dto.request.ProgressFilter;
import com.bioimpedance.dto.response.ClientProgressDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.pagination.PageResponse;
import com.bioimpedance.pagination.SortDirection;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Serviço dedicado ao cálculo de progresso dos clientes.
 *
 * Separação de responsabilidades: a lógica de progresso é reutilizada
 * tanto pelo Dashboard (Top 4) quanto pelo endpoint paginado /clients/progress.
 */
@Service
@RequiredArgsConstructor
public class ClientProgressService {

    private final ClientRepository clientRepository;
    private final AssessmentRepository assessmentRepository;
    private final CurrentUserService currentUserService;
    private final BillingService billingService;

    /**
     * Retorna o Top 4 de clientes com maior evolução (uso exclusivo do Dashboard).
     */
    public List<ClientProgressDTO> getTopProgress() {
        billingService.requireFeature(PlanFeature.BODY_COMPARISON);
        List<ClientProgressDTO> allProgress = getAllClientsWithProgress();

        return allProgress.stream()
            .sorted((a, b) -> {
                double scoreA = Math.abs(a.getBodyFatDiff()) + Math.abs(a.getLeanMassDiff());
                double scoreB = Math.abs(b.getBodyFatDiff()) + Math.abs(b.getLeanMassDiff());
                return Double.compare(scoreB, scoreA);
            })
            .limit(4)
            .toList();
    }

    /**
     * Listagem completa e paginada de evolução (uso da tela /clients/progress).
     *
     * NOTA ARQUITETURAL: O cálculo de progresso é feito em memória (busca todas
     * as avaliações do usuário, filtra e ordena). Isso é adequado para o volume
     * atual esperado (< 1000 clientes). Caso o volume cresça significativamente,
     * esta lógica deve ser migrada para uma consulta SQL paginada (ex: CTE ou
     * View materializada) para evitar sobrecarga de memória.
     */
    public PageResponse<ClientProgressDTO> getPagedProgress(ProgressFilter filter) {
        billingService.requireFeature(PlanFeature.BODY_COMPARISON);
        currentUserService.getCurrentUserId(); // garante autenticação

        List<ClientProgressDTO> allProgress = getAllClientsWithProgress();

        // Filtro por search (nome do cliente)
        if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
            String term = filter.getSearch().trim().toLowerCase(Locale.ROOT);
            allProgress = allProgress.stream()
                .filter(p -> p.getClientName().toLowerCase(Locale.ROOT).contains(term))
                .toList();
        }

        // Ordenação
        Comparator<ClientProgressDTO> comparator = switch (filter.getEffectiveSort()) {
            case "weightDiff" -> Comparator.comparingDouble(ClientProgressDTO::getWeightDiff);
            case "leanMassDiff" -> Comparator.comparingDouble(ClientProgressDTO::getLeanMassDiff);
            case "clientName" -> Comparator.comparing(ClientProgressDTO::getClientName);
            default -> Comparator.comparingDouble(p ->
                Math.abs(p.getBodyFatDiff()) + Math.abs(p.getLeanMassDiff()));
        };

        // CORREÇÃO: O comparator acima é ASC por padrão. Se a direção for DESC, invertemos.
        if (filter.getDirection() == SortDirection.DESC) {
            comparator = comparator.reversed();
        }

        List<ClientProgressDTO> sorted = allProgress.stream().sorted(comparator).toList();

        // Paginação manual em memória
        int start = filter.getPage() * filter.getSize();
        int end = Math.min(start + filter.getSize(), sorted.size());
        List<ClientProgressDTO> pageContent = (start < sorted.size())
            ? sorted.subList(start, end)
            : List.of();

        int totalPages = (int) Math.ceil((double) sorted.size() / filter.getSize());

        return new PageResponse<>(
            pageContent,
            filter.getPage(),
            filter.getSize(),
            sorted.size(),
            totalPages,
            filter.getPage() >= totalPages - 1
        );
    }

    private List<ClientProgressDTO> getAllClientsWithProgress() {
        String userId = currentUserService.getCurrentUserId();
        List<ClientProgressDTO> result = new ArrayList<>();

        clientRepository.findByUserIdOrderByCreatedAtDesc(userId).forEach(client -> {
            List<Assessment> assessments = assessmentRepository
                .findByUserIdAndClientIdOrderByDateDescCreatedAtDesc(userId, client.getId());

            List<Assessment> validAssessments = assessments.stream()
                .filter(a -> a.getMethod() != AssessmentMethod.IMC)
                .filter(a -> a.getResult() != null)
                .filter(a -> a.getResult().getBodyFat() != null && a.getResult().getBodyFat() > 0)
                .filter(a -> a.getResult().getLeanMass() != null && a.getResult().getLeanMass() > 0)
                .toList();

            if (validAssessments.size() >= 2) {
                Assessment latest = validAssessments.get(0);
                Assessment previous = validAssessments.get(1);
                result.add(ClientProgressDTO.builder()
                    .clientId(client.getId())
                    .clientName(client.getName())
                    .clientGoal(client.getGoal())
                    .weightDiff(round2(latest.getWeight() - previous.getWeight()))
                    .bodyFatDiff(round2(latest.getResult().getBodyFat() - previous.getResult().getBodyFat()))
                    .leanMassDiff(round2(latest.getResult().getLeanMass() - previous.getResult().getLeanMass()))
                    .latestDate(latest.getDate().toString())
                    .previousDate(previous.getDate().toString())
                    .build());
            }
        });
        return result;
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}