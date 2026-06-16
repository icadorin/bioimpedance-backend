package com.bioimpedance.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
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