package com.bioimpedance.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bioimpedance.domain.contracts.SelectionNotAllowedException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Prefixos de mensagens que indicam erros de configuração interna.
     * Nunca devem ser expostos ao cliente — retornam mensagem genérica.
     */
    private static final List<String> INTERNAL_MESSAGE_PREFIXES = List.of(
        "STRIPE_SECRET_KEY",
        "Price ID",
        "webhook secret",
        "Configuração interna",
        "Erro ao realizar cálculo",
        "Erro interno de autenticação",
        "Erro ao gerar chave JWT",
        "JWT_SECRET"
    );

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    /**
     * IllegalArgumentException: mensagens de validação de negócio são seguras
     * para exibir. Mensagens de configuração interna são substituídas.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        String message = sanitize(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Dados inválidos", errors));
    }

    /**
     * Corpo da requisição inválido: JSON malformado, encoding incorreto,
     * tipo incompatível, etc. Lançado pelo Jackson antes de chegar ao controller.
     * <p>
     * Exemplos comuns:
     * - Caracteres UTF-8 mal codificados (ex: Git Bash Windows → curl.exe)
     * - JSON com sintaxe quebrada (vírgula extra, chave faltando)
     * - Tipo errado (string onde espera número)
     * - Enum inválido não capturado pelo ACCEPT_CASE_INSENSITIVE_ENUMS
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex) {
        log.warn("Corpo da requisição inválido: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Corpo da requisição inválido (JSON malformado ou encoding incorreto)"));
    }

    /**
     * Guard de seleção (DEC-53) e motivo obrigatório (DEC-54) — doc.md §17.8.
     * Retorna 422 com code estável + variantId/conversionId + status + reasons,
     * para o front exibir o motivo sem recalcular nada por conta própria.
     */
    @ExceptionHandler(SelectionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleSelectionNotAllowed(SelectionNotAllowedException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("code", ex.getCode());
        errors.put("variantId", ex.getTargetId());
        if (ex.getStatus() != null) {
            errors.put("status", ex.getStatus());
        }
        String message = ex.getReasons().isEmpty()
            ? ex.getMessage()
            : String.join("; ", ex.getReasons());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
            .body(new ErrorResponse(HttpStatus.UNPROCESSABLE_CONTENT.value(), message, errors));
    }

    /**
     * Avaliação finalizada travada (Fase 13 — DEC-58): edição, medidas,
     * cálculo ou nova finalização → 409 ASSESSMENT_LOCKED.
     */
    @ExceptionHandler(AssessmentLockedException.class)
    public ResponseEntity<ErrorResponse> handleAssessmentLocked(AssessmentLockedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("code", "ASSESSMENT_LOCKED");
        errors.put("assessmentId", ex.getAssessmentId());
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), errors));
    }

    /**
     * Autosave recusado pelo MeasurementValidator (Fase 13 — DEC-56):
     * 422 MEASUREMENT_INVALID com issues estruturados (doc.md §17.8).
     */
    @ExceptionHandler(MeasurementInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleMeasurementInvalid(MeasurementInvalidException ex) {
        List<Map<String, String>> issues = ex.getIssues().stream()
            .map(issue -> {
                Map<String, String> m = new LinkedHashMap<>();
                m.put("type", issue.type().name());
                m.put("inputId", issue.inputId() != null ? issue.inputId() : "");
                m.put("message", issue.message());
                return m;
            })
            .toList();
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", HttpStatus.UNPROCESSABLE_CONTENT.value());
        body.put("code", "MEASUREMENT_INVALID");
        body.put("inputId", ex.getInputId());
        body.put("message", "Medida inválida");
        body.put("issues", issues);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(body);
    }

    /**
     * RuntimeException genérica: loga internamente, retorna mensagem genérica ao cliente.
     * Impede que stack traces ou mensagens de infraestrutura vazem.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("Erro de runtime não tratado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor"));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException ex) {
        // Mensagens de segurança são intencionalmente genéricas para não enumerar estado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                "Sessão inválida. Faça login novamente."));
    }

    @ExceptionHandler(TwoFactorRequiredException.class)
    public ResponseEntity<ErrorResponse> handleTwoFactorRequired(TwoFactorRequiredException ex) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Erro não tratado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor"));
    }

    /**
     * Substitui mensagens que indicam detalhes de configuração interna
     * por uma mensagem genérica segura. Loga o original para auditoria.
     */
    private String sanitize(String message) {
        if (message == null) return "Requisição inválida";

        boolean isInternal = INTERNAL_MESSAGE_PREFIXES.stream()
            .anyMatch(prefix -> message.toLowerCase().contains(prefix.toLowerCase()));

        if (isInternal) {
            log.warn("Mensagem interna interceptada antes de chegar ao cliente: {}", message);
            return "Operação indisponível no momento. Tente novamente em instantes.";
        }

        return message;
    }
}