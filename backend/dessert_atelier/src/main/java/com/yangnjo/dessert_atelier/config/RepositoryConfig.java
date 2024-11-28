package com.yangnjo.dessert_atelier.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.yangnjo.dessert_atelier.repository")
@EnableRedisRepositories(basePackages = "com.yangnjo.dessert_atelier.redis", redisTemplateRef = "stringRedisTemplate")
public class RepositoryConfig {

}
