package com.yangnjo.dessert_atelier.provider;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
@Getter
public class AccessTokenProvider {

    @Value("${jwt.access-token.name}")
    private String name;

    @Value("${jwt.access-token.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token.duration}")
    private Integer duration;

    @Value("${jwt.access-token.time-unit}")
    private ChronoUnit timeUnit;

    private JwtProvider jwtProvider;

    @PostConstruct
    public void init() {
        this.jwtProvider = new JwtProvider(secretKey, duration, timeUnit);
    }

    public String create(String subject, final Map<String, Object> claims) {
        return jwtProvider.create(subject, claims, null);
    }

    public Claims validate(String jwt) {
        return jwtProvider.validate(jwt);
    }

    public Map<String, String> setAccessTokenHeader(String accessTokenString) {
        Map<String, String> headers = new HashMap<>();
        headers.put(name, accessTokenString);
        headers.put(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, name); // CORS 설정 추가
        return headers;
    }

}
