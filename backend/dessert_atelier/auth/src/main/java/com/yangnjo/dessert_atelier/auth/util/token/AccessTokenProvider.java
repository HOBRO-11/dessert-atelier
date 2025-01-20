package com.yangnjo.dessert_atelier.auth.util.token;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessTokenProvider extends TokenProviderBase {

    @Value("${jwt.access-token.duration}")
    private Integer duration;
    @Value("${jwt.access-token.name}")
    private String name;
    @Value("${jwt.access-token.path}")
    private String path;
    @Value("${jwt.access-token.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token.time-unit}")
    private ChronoUnit timeUnit;

    private final Environment env;

    @Override
    @PostConstruct
    public void init(){
        setDuration(duration);
        setName(name);
        setPath(path);
        setSecretKey(secretKey);
        setTimeUnit(timeUnit);
        setProd(List.of(env.getActiveProfiles()).contains("prod")); 
        super.init();
    }
}
