package com.bioimpedance.service;

import com.bioimpedance.constants.ClientStatus;
import com.bioimpedance.constants.PlanFeature;
import com.bioimpedance.dto.request.AssessmentRequestDTO;
import com.bioimpedance.dto.request.CalculateRequestDTO;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.dto.response.CalculationResultDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.AssessmentResult;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.mapper.AssessmentMapper;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final CalculationService calculationService;
    private final BillingService billingService;
    private final ClientRepository clientRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public AssessmentResponseDTO create(AssessmentRequestDTO dto) {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        Client client = clientRepository.findByIdAndUserId(dto.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        int age = calculateAge(client.getBirthDate(), dto.getDate().toLocalDate());

        CalculateRequestDTO calcRequest = enrichWithClientData(dto, client, age);
        CalculationResultDTO resultDTO = calculationService.calculate(calcRequest);

        Assessment assessment = buildAssessment(dto, client, age, userId, resultDTO);
        assessment = assessmentRepository.save(assessment);

        if (ClientStatus.PENDING.equals(client.getStatus())) {
            client.setStatus(ClientStatus.ACTIVE);
            clientRepository.saveAndFlush(client);
        }

        return assessmentMapper.toResponse(assessment);
    }

    public CalculationResultDTO calculate(CalculateRequestDTO dto) {
        if (dto.getClientId() == null || dto.getClientId().isBlank()) {
            return calculationService.calculate(dto);
        }

        String userId = currentUserService.getCurrentUserId();
        Client client = clientRepository.findByIdAndUserId(dto.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente nÃ£o encontrado"));

        LocalDate assessmentDate = dto.getDate() != null
            ? dto.getDate().toLocalDate()
            : LocalDate.now();
        int age = calculateAge(client.getBirthDate(), assessmentDate);

        return calculationService.calculate(enrichWithClientData(dto, client, age));
    }

    public List<AssessmentResponseDTO> findByClientId(String clientId) {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        if (!clientRepository.existsByIdAndUserId(clientId, userId)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        return assessmentRepository.findByUserIdAndClientIdOrderByDateDescCreatedAtDesc(userId, clientId)
            .stream()
            .map(assessmentMapper::toResponse)
            .toList();
    }

    public AssessmentResponseDTO findById(String id) {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        Assessment assessment = assessmentRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));
        return assessmentMapper.toResponse(assessment);
    }

    @Transactional
    public void delete(String id) {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        if (!assessmentRepository.existsByIdAndUserId(id, userId)) {
            throw new ResourceNotFoundException("Avaliação não encontrada");
        }

        assessmentRepository.deleteById(id);
    }

    // ==================== MÉTODOS PRIVADOS ====================

    private int calculateAge(LocalDate birthDate, LocalDate assessmentDate) {
        return Period.between(birthDate, assessmentDate).getYears();
    }

    private CalculateRequestDTO enrichWithClientData(AssessmentRequestDTO dto,
                                                     Client client,
                                                     int age) {
        return CalculateRequestDTO.builder()
            .method(dto.getMethod())
            .weight(dto.getWeight())
            .height(client.getHeight())
            .age(age)
            .gender(client.getGender())
            .activityLevel(dto.getActivityLevel())
            .objective(dto.getObjective())
            .waist(dto.getWaist())
            .neck(dto.getNeck())
            .hip(dto.getHip())
            .resistance(dto.getResistance())
            .reactance(dto.getReactance())
            .protocol(dto.getProtocol())
            .biceps(dto.getBiceps())
            .chest(dto.getChest())
            .midaxillary(dto.getMidaxillary())
            .triceps(dto.getTriceps())
            .subscapular(dto.getSubscapular())
            .abdominal(dto.getAbdominal())
            .suprailiac(dto.getSuprailiac())
            .thigh(dto.getThigh())
            .build();
    }

    private CalculateRequestDTO enrichWithClientData(CalculateRequestDTO dto,
                                                     Client client,
                                                     int age) {
        return CalculateRequestDTO.builder()
            .clientId(dto.getClientId())
            .date(dto.getDate())
            .method(dto.getMethod())
            .weight(dto.getWeight())
            .height(client.getHeight())
            .age(age)
            .gender(client.getGender())
            .activityLevel(dto.getActivityLevel())
            .objective(dto.getObjective())
            .waist(dto.getWaist())
            .neck(dto.getNeck())
            .hip(dto.getHip())
            .resistance(dto.getResistance())
            .reactance(dto.getReactance())
            .protocol(dto.getProtocol())
            .biceps(dto.getBiceps())
            .chest(dto.getChest())
            .midaxillary(dto.getMidaxillary())
            .triceps(dto.getTriceps())
            .subscapular(dto.getSubscapular())
            .abdominal(dto.getAbdominal())
            .suprailiac(dto.getSuprailiac())
            .thigh(dto.getThigh())
            .build();
    }

    private Assessment buildAssessment(AssessmentRequestDTO dto,
                                       Client client,
                                       int age,
                                       String userId,
                                       CalculationResultDTO resultDTO) {
        return Assessment.builder()
            .userId(userId)
            .clientId(client.getId())
            .date(dto.getDate().toLocalDate())
            .method(dto.getMethod())
            .weight(dto.getWeight())
            .height(client.getHeight())
            .age(age)
            .gender(client.getGender())
            .waist(dto.getWaist())
            .neck(dto.getNeck())
            .hip(dto.getHip())
            .resistance(dto.getResistance())
            .reactance(dto.getReactance())
            .protocol(dto.getProtocol())
            .biceps(dto.getBiceps())
            .chest(dto.getChest())
            .midaxillary(dto.getMidaxillary())
            .triceps(dto.getTriceps())
            .subscapular(dto.getSubscapular())
            .abdominal(dto.getAbdominal())
            .suprailiac(dto.getSuprailiac())
            .thigh(dto.getThigh())
            .observations(dto.getObservations())
            .result(toAssessmentResult(resultDTO))
            .build();
    }

    private AssessmentResult toAssessmentResult(CalculationResultDTO dto) {
        return AssessmentResult.builder()
            .imc(dto.getImc())
            .bodyFat(dto.getBodyFat())
            .leanMass(dto.getLeanMass())
            .fatMass(dto.getFatMass())
            .ffmi(dto.getFfmi())
            .bmr(dto.getBmr())
            .tdee(dto.getTdee())
            .targetCalories(dto.getTargetCalories())
            .bodyFatLevel(dto.getBodyFatLevel())
            .build();
    }
}
