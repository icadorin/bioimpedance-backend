package com.bioimpedance.security;

import com.bioimpedance.entity.User;
import com.bioimpedance.repository.UserRepository;
import com.bioimpedance.service.JwtService;
import com.bioimpedance.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final FingerprintService fingerprintService;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (!isSafeMethod(request)) {
                String csrfHeader = request.getHeader("X-XSRF-TOKEN");
                String csrfCookie = cookieUtil.getCsrfToken(request).orElse(null);
                if (csrfHeader == null || csrfCookie == null || !csrfHeader.equals(csrfCookie)) {
                    log.warn("CSRF inválido para {} — header={}, cookie={}", path, csrfHeader, csrfCookie != null ? "presente" : "ausente");
                    writeError(response, HttpServletResponse.SC_FORBIDDEN, "CSRF token inválido");
                    return;
                }
            }

            String token = cookieUtil.getAccessToken(request).orElse(null);
            if (token == null) {
                log.warn("Token de acesso ausente para {}", path);
                writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "Não autenticado");
                return;
            }

            if (!jwtService.isTokenValid(token)) {
                log.warn("Token inválido ou expirado para {}", path);
                writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "Sessão expirada");
                return;
            }

            String email = jwtService.extractEmail(token);
            String tokenFamily = jwtService.extractTokenFamily(token);

            User user = userRepository.findByEmail(email).orElse(null);

            if (user != null && user.isTwoFactorEnabled()) {
                boolean twoFaVerified = jwtService.extractTwoFactorVerified(token);
                if (!twoFaVerified) {
                    writeError(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "2FA obrigatório");
                    return;
                }
            }

            // 5. Valida fingerprint
            if (user != null) {
                FingerprintService.FingerprintResult result =
                    fingerprintService.validateFingerprint(user.getId(), tokenFamily, request);

                if (result.isBlocked()) {
                    writeError(response, HttpServletResponse.SC_UNAUTHORIZED,
                        "Sessão suspeita detectada. Faça login novamente.");
                    return;
                }
            }

            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, null, null)
            );

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Erro inesperado no filtro JWT para {}: {}", path, e.getMessage(), e);
            writeError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erro interno de autenticação");
        }
    }

    private boolean isSafeMethod(HttpServletRequest request) {
        return switch (request.getMethod().toUpperCase()) {
            case "GET", "HEAD", "OPTIONS", "TRACE" -> true;
            default -> false;
        };
    }

    private boolean isPublicEndpoint(String path) {
        return path.equals("/api/auth/login")
            || path.equals("/api/auth/register")
            || path.equals("/api/auth/refresh")
            || path.equals("/api/auth/logout")
            || path.equals("/api/auth/2fa/verify")
            || path.startsWith("/api/billing/webhook");
    }

    private void writeError(HttpServletResponse response, int status, String message)
        throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}