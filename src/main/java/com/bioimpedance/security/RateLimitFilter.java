package com.bioimpedance.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Cache<String, Bucket> loginBuckets = Caffeine.newBuilder()
        .expireAfterAccess(5, TimeUnit.MINUTES)
        .maximumSize(10_000)
        .build();

    private final Cache<String, Bucket> registerBuckets = Caffeine.newBuilder()
        .expireAfterAccess(30, TimeUnit.MINUTES)
        .maximumSize(10_000)
        .build();

    private final Cache<String, Bucket> twoFactorBuckets = Caffeine.newBuilder()
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .maximumSize(10_000)
        .build();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if ("POST".equalsIgnoreCase(method)) {
            if (path.equals("/api/auth/login")) {
                if (isRateLimited(loginBuckets, getClientIp(request), 5, 1)) {
                    writeTooManyRequests(response, "Muitas tentativas de login. Tente novamente em 1 minuto.");
                    return;
                }
            } else if (path.equals("/api/auth/register")) {
                if (isRateLimited(registerBuckets, getClientIp(request), 3, 10)) {
                    writeTooManyRequests(response, "Limite de registros atingido.");
                    return;
                }
            } else if (path.equals("/api/auth/2fa/verify")) {
                if (isRateLimited(twoFactorBuckets, getClientIp(request), 5, 2)) {
                    writeTooManyRequests(response, "Muitas tentativas de verificação 2FA. Aguarde.");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    // true = bloqueado (atingiu o limite), false = liberado (pode prosseguir)
    private boolean isRateLimited(Cache<String, Bucket> cache, String key,
                                  int capacity, int refillPerMinute) {
        Bucket bucket = cache.get(key, k ->
            Bucket.builder()
                .addLimit(limit -> limit
                    .capacity(capacity)
                    .refillGreedy(refillPerMinute, Duration.ofMinutes(1)))
                .build());

        return !bucket.tryConsume(1);  // invertido aqui dentro
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
        return request.getRemoteAddr();
    }

    private void writeTooManyRequests(HttpServletResponse response, String message)
        throws IOException {
        response.setStatus(429);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}