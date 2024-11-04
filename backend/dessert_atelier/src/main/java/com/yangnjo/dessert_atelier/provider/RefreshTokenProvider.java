package com.yangnjo.dessert_atelier.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
@Component
public class RefreshTokenProvider {

    private static final String COOKIE_HEADER = "%s=%s; Path=%s; Max-Age=%d; Secure; HttpOnly; SameSite=Strict";

    @Value("${jwt.refresh-token.name}")
    private String name;

    @Value("${jwt.refresh-token.secret-key}")
    private String secretKey;

    @Value("${jwt.refresh-token.hash-fields}")
    private List<String> hashFields;

    @Value("${jwt.refresh-token.duration}")
    private Integer duration;

    @Value("${jwt.refresh-token.time-unit}")
    private ChronoUnit timeUnit;

    @Value("${jwt.refresh-token.path}")
    private String path;

    private JwtProvider jwtProvider;

    @PostConstruct
    public void init() {
        this.jwtProvider = new JwtProvider(secretKey, duration, timeUnit);
    }

    public String create(String subject, HttpServletRequest request) {
        List<String> hashValues = getHashValues(request);
        return jwtProvider.create(subject, hashValues);
    }

    public String validate(String jwt, HttpServletRequest request) {
        List<String> hashValues = getHashValues(request);
        return jwtProvider.validate(jwt, hashValues);
    }

    public LocalDateTime getExpiredDuration() {
        return LocalDateTime.now().plus(duration, timeUnit);
    }

    public LocalDateTime getExpiredDateTime(String refreshTokenString) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return jwtProvider.getExpDate(refreshTokenString, key);
    }

    public void setRefreshTokenToCookie(HttpServletResponse response, String refreshTokenString) {
        int maxAge = getMaxAge();
        
        Cookie refreshToken = new Cookie(name, refreshTokenString);
        refreshToken.setHttpOnly(true);
        refreshToken.setSecure(true);
        refreshToken.setPath(path);
        refreshToken.setMaxAge(maxAge);
        
        String cookieHeader = String.format(COOKIE_HEADER,
                refreshToken.getName(),
                refreshToken.getValue(),
                refreshToken.getPath(),
                refreshToken.getMaxAge());

        response.setHeader("Set-Cookie", cookieHeader);
        // response.addCookie(refreshToken);
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

        if (hashFields.isEmpty() == false) {
            for (String field : hashFields) {
                hashValues.add(request.getAttribute(field).toString());
            }
        }

        return hashValues;
    }
}
