package com.yangnjo.dessert_atelier.provider;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider extends TokenProviderBase {

    @Value("${jwt.refresh-token.duration}")
    private Integer duration;
    @Value("${jwt.refresh-token.hash-fields}")
    private List<String> hashFields;
    @Value("${jwt.refresh-token.name}")
    private String name;
    @Value("${jwt.refresh-token.path}")
    private String path;
    @Value("${jwt.refresh-token.secret-key}")
    private String secretKey;
    @Value("${jwt.refresh-token.time-unit}")
    private ChronoUnit timeUnit;

    private final Environment env;

    @Override
    @PostConstruct
    public void init() {
        setDuration(duration);
        setHashFields(hashFields);
        setName(name);
        setPath(path);
        setSecretKey(secretKey);
        setTimeUnit(timeUnit);
        setProd(List.of(env.getActiveProfiles()).contains("prod")); 
        super.init();
    }

}
