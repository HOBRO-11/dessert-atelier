package com.yangnjo.dessert_atelier.provider;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
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

    // FIXME: 이렇게 ACCESS TOKEN을 전달하는 것이 맞나 의문이다.
    public void setAccessToken(HttpServletResponse response, String accessTokenString) {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            String jsonResponse = "{ \"" + name + "\" : " + accessTokenString + "\"}";
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
