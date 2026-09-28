package com.bioimpedance.service;

import com.bioimpedance.constants.ClientStatus;
import com.bioimpedance.constants.PlanFeature;
import com.bioimpedance.dto.request.AssessmentFilter;
import com.bioimpedance.dto.request.AssessmentFlowRequestDTO;
import com.bioimpedance.dto.request.AssessmentRequestDTO;
import com.bioimpedance.dto.response.AssessmentResponseDTO;
import com.bioimpedance.dto.response.CalculationFlowResponseDTO;
import com.bioimpedance.entity.Assessment;
import com.bioimpedance.entity.Client;
import com.bioimpedance.exception.ResourceNotFoundException;
import com.bioimpedance.mapper.AssessmentMapper;
import com.bioimpedance.pagination.PageResponse;
import com.bioimpedance.pagination.PageableUtils;
import com.bioimpedance.repository.AssessmentRepository;
import com.bioimpedance.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Serviço de avaliações físicas (Fase 12 / Chunk 4 — DEC-32/35/36/42).
 * <p>
 * O cálculo científico é delegado ao {@link AssessmentFlowService} (orchestrator);
 * as métricas derivadas (IMC/BMR/TDEE/FFMI/%G + recomendação) são aplicadas
 * DEPOIS do resultado final via {@link MetabolicService} (DEC-35).
 * <p>
 * DEC-42: quem persiste Assessment + measurements é o fluxo (Chunk 3); este
 * serviço apenas enriquece a linha persistida com o resultado derivado e com
 * os ecos de perfil que os leitores legacy ainda usam.
 */
@Service
@RequiredArgsConstructor
public class AssessmentService {

    /**
     * Mapa declarativo inputId canônico → getter do DTO legacy.
     * Única fonte de verdade do mapeamento legacy → novo fluxo:
     * adicionar uma medida nova = 1 linha aqui (DEC-42).
     */
    private static final Map<String, Function<AssessmentRequestDTO, Double>> MEASUREMENT_MAP =
        Map.ofEntries(
            Map.entry("BODY_MASS",               AssessmentRequestDTO::getWeight),
            Map.entry("CIRCUMFERENCE_ABDOMEN",   AssessmentRequestDTO::getWaist),
            Map.entry("CIRCUMFERENCE_NECK",      AssessmentRequestDTO::getNeck),
            Map.entry("CIRCUMFERENCE_HIP",       AssessmentRequestDTO::getHip),
            Map.entry("BIOIMPEDANCE_RESISTANCE", AssessmentRequestDTO::getResistance),
            Map.entry("BIOIMPEDANCE_REACTANCE",  AssessmentRequestDTO::getReactance),
            Map.entry("SKINFOLD_BICEPS",         AssessmentRequestDTO::getBiceps),
            Map.entry("SKINFOLD_PECTORAL",       AssessmentRequestDTO::getChest),
            Map.entry("SKINFOLD_AXILLARY_MID",   AssessmentRequestDTO::getMidaxillary),
            Map.entry("SKINFOLD_TRICEPS",        AssessmentRequestDTO::getTriceps),
            Map.entry("SKINFOLD_SUBSCAPULAR",    AssessmentRequestDTO::getSubscapular),
            Map.entry("SKINFOLD_ABDOMEN",        AssessmentRequestDTO::getAbdominal),
            Map.entry("SKINFOLD_SUPRAILIAC",     AssessmentRequestDTO::getSuprailiac),
            Map.entry("SKINFOLD_THIGH",          AssessmentRequestDTO::getThigh)
        );

    private final AssessmentRepository assessmentRepository;
    private final AssessmentMapper assessmentMapper;
    private final AssessmentFlowService assessmentFlowService;
    private final MetabolicService metabolicService;
    private final BillingService billingService;
    private final ClientRepository clientRepository;
    private final CurrentUserService currentUserService;

    /**
     * Cria a avaliação: o fluxo científico persiste Assessment + measurements +
     * audit (Chunk 3); aqui enriquecemos a linha com métricas derivadas (DEC-35)
     * e ecos de perfil para leitores legacy (DEC-42).
     */
    @Transactional
    public AssessmentResponseDTO create(AssessmentRequestDTO dto) {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        Client client = clientRepository.findByIdAndUserId(dto.getClientId(), userId)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        int age = calculateAge(client.getBirthDate(), dto.getDate().toLocalDate());

        // Fluxo científico: persiste Assessment + measurements + AuditSnapshot.
        CalculationFlowResponseDTO flowResponse =
            assessmentFlowService.calculate(userId, toFlowRequest(dto));

        Assessment assessment = assessmentRepository.findById(flowResponse.getAssessmentId())
            .orElseThrow(() -> new IllegalStateException(
                "Assessment não persistida pelo fluxo: " + flowResponse.getAssessmentId()));

        // Métricas derivadas DEPOIS do resultado final (DEC-35).
        assessment.setResult(metabolicService.buildResult(
            client,
            age,
            dto.getWeight(),
            dto.getActivityLevel(),
            dto.getObjective(),
            flowResponse,
            flowResponse.getAuditId()
        ));
        assessment.setObservations(dto.getObservations());

        // Ecos de perfil para leitores legacy (ClientProgressService/Dashboard).
        // DEC-42: mantidos até o Chunk 5 migrar esses leitores para measurements;
        // as colunas fixas de MEDIDA (dobras/circ/bio) NÃO são mais escritas (DEC-36).
        assessment.setWeight(dto.getWeight());
        assessment.setHeight(client.getHeight());
        assessment.setAge(age);
        assessment.setGender(client.getGender());

        assessment = assessmentRepository.save(assessment);

        if (ClientStatus.PENDING.equals(client.getStatus())) {
            client.setStatus(ClientStatus.ACTIVE);
            clientRepository.saveAndFlush(client);
        }

        return assessmentMapper.toResponse(assessment);
    }

    /**
     * Cálculo explícito sem persistência de resultado derivado (doc.md §25).
     * O novo fluxo sempre exige cliente: o perfil dirige aplicabilidade
     * (doc.md §4/§8) — o calculate anônimo do legacy não existe mais.
     */
    public CalculationFlowResponseDTO calculate(AssessmentFlowRequestDTO flowRequest) {
        String userId = currentUserService.getCurrentUserId();
        return assessmentFlowService.calculate(userId, flowRequest);
    }

    // ==================== LEITURA (inalterados) ====================

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

    public PageResponse<AssessmentResponseDTO> findPaged(AssessmentFilter filter) {
        billingService.requireFeature(PlanFeature.HISTORY);
        String userId = currentUserService.getCurrentUserId();

        if (filter.getClientId() != null && !filter.getClientId().isBlank()) {
            if (!clientRepository.existsByIdAndUserId(filter.getClientId(), userId)) {
                throw new ResourceNotFoundException("Cliente não encontrado");
            }
        }

        String clientId = (filter.getClientId() != null && !filter.getClientId().isBlank())
            ? filter.getClientId()
            : null;

        Page<Assessment> page = assessmentRepository.findPaged(
            userId,
            clientId,
            filter.getMethod(),
            filter.getFrom(),
            filter.getTo(),
            PageableUtils.of(filter)
        );

        List<String> clientIds = page.getContent().stream()
            .map(Assessment::getClientId)
            .distinct()
            .toList();

        Map<String, String> clientNameMap = clientRepository.findAllById(clientIds).stream()
            .collect(Collectors.toMap(Client::getId, Client::getName));

        Page<AssessmentResponseDTO> mapped = page.map(assessment -> {
            AssessmentResponseDTO dto = assessmentMapper.toResponse(assessment);
            dto.setClientName(clientNameMap.get(assessment.getClientId()));
            return dto;
        });

        return PageResponse.of(mapped);
    }

    // ==================== HELPERS ====================

    private int calculateAge(LocalDate birthDate, LocalDate assessmentDate) {
        return Period.between(birthDate, assessmentDate).getYears();
    }

    /** Extrai só as medidas presentes no DTO, no formato inputId → valor. */
    private Map<String, Double> extractMeasurements(AssessmentRequestDTO dto) {
        Map<String, Double> measurements = new LinkedHashMap<>();
        MEASUREMENT_MAP.forEach((inputId, getter) -> {
            Double value = getter.apply(dto);
            if (value != null) {
                measurements.put(inputId, value);
            }
        });
        return measurements;
    }

    /** DTO legacy → request do novo fluxo (contexto científico fica null = NOT_DOCUMENTED). */
    private AssessmentFlowRequestDTO toFlowRequest(AssessmentRequestDTO dto) {
        return AssessmentFlowRequestDTO.builder()
            .clientId(dto.getClientId())
            .date(dto.getDate().toLocalDate())
            .nutritionObjective(dto.getObjective())
            .measurements(extractMeasurements(dto))
            .build();
    }
}