package com.yangnjo.dessert_atelier.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Setter;

@Setter(value = AccessLevel.PROTECTED)
public class TokenProviderBase {

    private static final String PROD_TOKEN_COOKIE_HEADER = "%s=%s; Path=%s; Max-Age=%d; Secure; HttpOnly; SameSite=strict";
    private static final String DEV_TOKEN_COOKIE_HEADER = "%s=%s; Path=%s; Max-Age=%d; HttpOnly; SameSite=strict";
    private static final String LOGIN_CHECK_COOKIE_HEADER = "%s=%s; Path=%s; Max-Age=%d; HttpOnly; SameSite=strict";
    private static final String CLEAR_TOKEN_HEADER = "%s=; Path=%s; Max-Age=0; HttpOnly; SameSite=strict";

    private String name;
    private String secretKey;
    private List<String> hashFields;
    private Integer duration;
    private ChronoUnit timeUnit;
    private String path;
    private boolean isProd = false;

    private JwtProvider jwtProvider;

    public void init() {
        this.jwtProvider = new JwtProvider(secretKey, duration, timeUnit);
    }

    public String create(String subject, Map<String, Object> claims, HttpServletRequest request) {
        List<String> hashValues = getHashValues(request);
        return jwtProvider.create(subject, claims, hashValues);
    }

    public Claims validate(String jwt, HttpServletRequest request) {
        List<String> hashValues = getHashValues(request);
        return jwtProvider.validate(jwt, hashValues);
    }

    public List<TokenHeader> clearTokenHeader() {
        return List.of(new TokenHeader(HttpHeaders.SET_COOKIE, String.format(CLEAR_TOKEN_HEADER, name, path)),
                new TokenHeader(HttpHeaders.SET_COOKIE, String.format(CLEAR_TOKEN_HEADER, "LoginChecker", "")));
    }

    public LocalDateTime getExpiredDuration() {
        return LocalDateTime.now().plus(duration, timeUnit);
    }

    public LocalDateTime getExpiredDateTime(String refreshTokenString) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return jwtProvider.getExpDate(refreshTokenString, key);
    }

    public TokenHeader getTokenHeader(String refreshTokenString) {
        return new TokenHeader(HttpHeaders.SET_COOKIE, getTokenBody(refreshTokenString));
    }

    private String getTokenBody(String body) {
        if (isProd) {
            return String.format(PROD_TOKEN_COOKIE_HEADER, name, body, path, getMaxAge());
        } else {
            return String.format(DEV_TOKEN_COOKIE_HEADER, name, body, path, getMaxAge());
        }
    }

    public TokenHeader getLoginCheckHeader() {
        String loginCheckHeader = String.format(LOGIN_CHECK_COOKIE_HEADER, "LoginChecker", "true", "/", getMaxAge());
        return new TokenHeader(HttpHeaders.SET_COOKIE, loginCheckHeader);
    }

    public String getSignature(String refreshToken) {
        String[] parts = refreshToken.split("\\.");
        if (parts.length == 3) {
            return parts[2]; // 시그니처는 마지막 부분
        }
        throw new RuntimeException("Invalid refresh token format");
    }

    private Integer getMaxAge() {
        return (int) timeUnit.getDuration().getSeconds() * duration;
    }

    private List<String> getHashValues(HttpServletRequest request) {
        List<String> hashValues = new ArrayList<>();

        if (request != null) {
            if (hashFields != null && (hashFields.isEmpty() == false)) {
                for (String field : hashFields) {
                    hashValues.add(request.getAttribute(field).toString());
                }
            }
        }

        return hashValues;
    }
}
