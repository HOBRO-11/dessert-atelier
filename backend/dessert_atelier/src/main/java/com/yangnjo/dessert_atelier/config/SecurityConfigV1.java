package com.yangnjo.dessert_atelier.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.filter.AccessTokenAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
@RequiredArgsConstructor
public class SecurityConfigV1 {

    private static final String DOMAIN_AND_PORT = "http://localhost:8070";
    private static final String GET = HttpMethod.GET.name();
    private static final String POST = HttpMethod.POST.name();
    private static final String PUT = HttpMethod.PUT.name();
    private static final String DELETE = HttpMethod.DELETE.name();
    private static final String PATCH = HttpMethod.PATCH.name();

    private final DefaultOAuth2UserService oAuth2JoinService;
    private final AccessTokenAuthenticationFilter accessTokenAuthenticationFilter;
    private final SimpleUrlAuthenticationSuccessHandler successHandler;
    private final LogoutHandler refreshTokenLogoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/members/**", "/api/baskets/**", "/api/addresses/")
                        .hasRole(MemberRole.MEMBER.getRole())
                        .anyRequest().permitAll())
                .exceptionHandling(
                        exHandling -> exHandling
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.sendRedirect("/access-deny");
                                }).authenticationEntryPoint((request, response, authException) -> {
                                    String refreshTokenPath = "/auth/refresh";
                                    response.sendRedirect(refreshTokenPath);
                                }))
                .addFilterBefore(accessTokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endpoint -> endpoint
                                .baseUri("/auth/oauth2/callback/**"))
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2JoinService))
                        .successHandler(successHandler))
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("refreshToken")
                        .addLogoutHandler(refreshTokenLogoutHandler));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        // refresh token
        CorsConfiguration config1 = new CorsConfiguration();
        config1.setAllowedOrigins(Arrays.asList(DOMAIN_AND_PORT));
        config1.setAllowedMethods(Arrays.asList(GET, POST, PUT, DELETE, PATCH));
        config1.setAllowedHeaders(Arrays.asList("*"));
        config1.setAllowCredentials(true);
        config1.setExposedHeaders(Arrays.asList("access-token", "Set-Cookie")); // AccessToken 헤더 노출

        UrlBasedCorsConfigurationSource source1 = new UrlBasedCorsConfigurationSource();
        source1.registerCorsConfiguration("/", config1);
        source1.registerCorsConfiguration("/auth/**", config1);

        // api require authentication -> use access token
        CorsConfiguration config2 = new CorsConfiguration();
        config2.setAllowedOrigins(Arrays.asList(DOMAIN_AND_PORT));
        config2.setAllowedMethods(Arrays.asList(GET, POST, PUT, DELETE, PATCH));
        config2.setAllowedHeaders(Arrays.asList("Authorization", "*"));
        config2.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source2 = new UrlBasedCorsConfigurationSource();
        source2.registerCorsConfiguration("/api/**", config2);

        return source1;
    }
}
