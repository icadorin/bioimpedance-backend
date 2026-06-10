package com.bioimpedance.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {

    private final boolean secureCookies;
    private final String refreshPath;

    public CookieUtil(
        @Value("${cookie.secure:true}") boolean secureCookies,
        @Value("${cookie.refresh-path:/api/auth/refresh}") String refreshPath
    ) {
        this.secureCookies = secureCookies;
        this.refreshPath = refreshPath;
    }

    private static final String ACCESS_TOKEN  = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String CSRF_TOKEN    = "XSRF-TOKEN";
    private static final Duration ACCESS_MAX_AGE = Duration.ofMinutes(15);
    private static final Duration CSRF_MAX_AGE = Duration.ofDays(1);
    private static final Duration REMEMBER_REFRESH_MAX_AGE = Duration.ofDays(30);
    private static final Duration DEFAULT_REFRESH_MAX_AGE = Duration.ofDays(7);

    public void setAccessToken(HttpServletResponse response, String token) {
        setAccessToken(response, token, ACCESS_MAX_AGE);
    }

    public void setAccessToken(HttpServletResponse response, String token, Duration maxAge) {
        addCookie(response, ResponseCookie.from(ACCESS_TOKEN, token)
            .httpOnly(true)
            .secure(secureCookies)
            .sameSite(secureCookies ? "Strict" : "Lax")
            .path("/api")
            .maxAge(maxAge)
            .build());
    }

    public void setRefreshToken(HttpServletResponse response, String token) {
        setRefreshToken(response, token, false);
    }

    public void setRefreshToken(HttpServletResponse response, String token, boolean rememberMe) {
        addCookie(response, ResponseCookie.from(REFRESH_TOKEN, token)
            .httpOnly(true)
            .secure(secureCookies)
            .sameSite(secureCookies ? "Strict" : "Lax")
            .path(refreshPath)
            .maxAge(rememberMe ? REMEMBER_REFRESH_MAX_AGE : DEFAULT_REFRESH_MAX_AGE)
            .build());
    }

    public void setCsrfToken(HttpServletResponse response, String token) {
        setCsrfToken(response, token, CSRF_MAX_AGE);
    }

    public void setCsrfToken(HttpServletResponse response, String token, Duration maxAge) {
        addCookie(response, ResponseCookie.from(CSRF_TOKEN, token)
            .httpOnly(false)
            .secure(secureCookies)
            .sameSite(secureCookies ? "Strict" : "Lax")
            .path("/")
            .maxAge(maxAge)
            .build());
    }

    public Optional<String> getAccessToken(HttpServletRequest request) {
        return getCookieValue(request, ACCESS_TOKEN);
    }

    public Optional<String> getRefreshToken(HttpServletRequest request) {
        return getCookieValue(request, REFRESH_TOKEN);
    }

    public Optional<String> getCsrfToken(HttpServletRequest request) {
        return getCookieValue(request, CSRF_TOKEN);
    }

    public void clearCookies(HttpServletResponse response) {
        addCookie(response, ResponseCookie.from(ACCESS_TOKEN, "")
            .httpOnly(true).secure(secureCookies)
            .sameSite(secureCookies ? "Strict" : "Lax")
            .path("/api").maxAge(0).build());

        addCookie(response, ResponseCookie.from(REFRESH_TOKEN, "")
            .httpOnly(true).secure(secureCookies)
            .sameSite(secureCookies ? "Strict" : "Lax")
            .path(refreshPath)
            .maxAge(0).build());

        addCookie(response, ResponseCookie.from(CSRF_TOKEN, "")
            .httpOnly(false).secure(secureCookies)
            .sameSite(secureCookies ? "Strict" : "Lax")
            .path("/").maxAge(0).build());
    }

    private void addCookie(HttpServletResponse response, ResponseCookie cookie) {
        response.addHeader("Set-Cookie", cookie.toString());
    }

    private Optional<String> getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();
        return Arrays.stream(request.getCookies())
            .filter(c -> name.equals(c.getName()))
            .map(Cookie::getValue)
            .findFirst();
    }
}