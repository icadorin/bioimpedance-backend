package com.bioimpedance.service;

import com.bioimpedance.constants.PlanFeature;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.dto.response.ClientProgressDTO;
import com.bioimpedance.dto.response.DashboardStatsDTO;
import com.bioimpedance.mapper.AssessmentMapper;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ClientRepository clientRepository;
    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final BillingService billingService;
    private final CurrentUserService currentUserService;
    private final ClientProgressService clientProgressService;

    public DashboardStatsDTO getDashboardStats() {
        billingService.requireFeature(PlanFeature.CHARTS);
        String userId = currentUserService.getCurrentUserId();

        long totalClients = clientRepository.countByUserId(userId);
        long activeClients = clientRepository.countByUserIdAndStatus(
            userId,
            com.bioimpedance.constants.ClientStatus.ACTIVE
        );

        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        long assessmentsThisMonth = assessmentRepository.countByUserIdAndDateBetween(
            userId,
            startOfMonth,
            endOfMonth
        );

        // Cálculo do progresso médio
        List<ClientProgressDTO> progressList = getClientsWithProgress();
        double avgBodyFatChange = 0;
        double avgLeanMassChange = 0;
        int clientsWithProgress = progressList.size();

        if (clientsWithProgress > 0) {
            avgBodyFatChange = progressList.stream()
                .mapToDouble(ClientProgressDTO::getBodyFatDiff)
                .average()
                .orElse(0);
            avgLeanMassChange = progressList.stream()
                .mapToDouble(ClientProgressDTO::getLeanMassDiff)
                .average()
                .orElse(0);
        }

        return DashboardStatsDTO.builder()
            .totalClients((int) totalClients)
            .activeClients((int) activeClients)
            .assessmentsThisMonth((int) assessmentsThisMonth)
            .averageBodyFatChange(round2(avgBodyFatChange))
            .averageLeanMassChange(round2(avgLeanMassChange))
            .clientsWithProgress(clientsWithProgress)
            .build();
    }

    public List<ClientProgressDTO> getClientsWithProgress() {
        return clientProgressService.getTopProgress();
    }

    public List<AssessmentResponseDTO> getRecentAssessments() {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        return assessmentRepository.findTop5ByUserIdOrderByDateDescCreatedAtDesc(userId)
            .stream()
            .map(assessmentMapper::toResponse)
            .collect(Collectors.toList());
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
