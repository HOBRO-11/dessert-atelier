package com.yangnjo.dessert_atelier.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
@RequiredArgsConstructor
public class SecurityConfigV1 {

    // @Value("${jwt.refresh-token.name}")
    // private String refreshTokenName;

    // @Value("${jwt.access-token.name}")
    // private String accessTokenName;

    // @Value("${jwt.refresh-token.path}")
    // private String refreshTokenPath;

    // private static final String DOMAIN_AND_PORT = "http://localhost:8070";
    // private static final String GET = HttpMethod.GET.name();
    // private static final String POST = HttpMethod.POST.name();
    // private static final String PUT = HttpMethod.PUT.name();
    // private static final String DELETE = HttpMethod.DELETE.name();
    // private static final String PATCH = HttpMethod.PATCH.name();
    // private static final String POLICY_DIRECTIVES = "default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self'; connect-src 'self'; frame-src 'self'; object-src 'none'; font-src 'none'; frame-ancestors 'none'";

    // private final DefaultOAuth2UserService oAuth2JoinService;
    // private final AccessTokenAuthenticationFilter accessTokenAuthenticationFilter;
    // private final SimpleUrlAuthenticationSuccessHandler successHandler;
    // private final LogoutHandler logoutHandler;

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //             .headers(headers -> headers
    //                     .cacheControl(CacheControlConfig::disable)
    //                     .crossOriginEmbedderPolicy(coep -> coep.policy(CrossOriginEmbedderPolicy.REQUIRE_CORP))
    //                     .crossOriginOpenerPolicy(coop -> coop.policy(CrossOriginOpenerPolicy.SAME_ORIGIN))
    //                     // .crossOriginResourcePolicy(corp -> corp.policy(CrossOriginResourcePolicy.SAME_ORIGIN))
    //                     // .contentSecurityPolicy(csp -> csp.policyDirectives(POLICY_DIRECTIVES))
    //                     .frameOptions(FrameOptionsConfig::deny)
    //                     .referrerPolicy(referrer -> referrer.policy(ReferrerPolicy.NO_REFERRER)) // 디버깅
    //                     .xssProtection(xss -> xss.headerValue(HeaderValue.ENABLED_MODE_BLOCK))
    //                     // .httpStrictTransportSecurity(hsts ->
    //                     // hsts.maxAgeInSeconds(31536000).includeSubDomains(true).preload(true)) //
    //                     // https 인증
    //                     .permissionsPolicy(permission -> permission.policy("payment=()")))
    //             .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    //             .csrf(CsrfConfigurer::disable)
    //             // .csrf(csrf -> csrf.ignoringRequestMatchers("/", "/auth/login", ))
    //             .httpBasic(HttpBasicConfigurer::disable)
    //             .formLogin(FormLoginConfigurer::disable)
    //             .sessionManagement(sessionManagement -> sessionManagement
    //                     .sessionCreationPolicy(SessionCreationPolicy.NEVER))
    //             // .authorizeHttpRequests(request -> request
    //             //         .requestMatchers("/api/members/**", "/api/baskets/**", "/api/addresses/")
    //             //         .hasRole(MemberRole.MEMBER.getRole())
    //             //         .anyRequest().permitAll())
    //             .exceptionHandling(
    //                     exHandling -> exHandling
    //                             .accessDeniedHandler((request, response, accessDeniedException) -> {
    //                                 response.setStatus(403);
    //                             }).authenticationEntryPoint((request, response, authException) -> {
    //                                 String refreshTokenPath = "/auth/refresh";
    //                                 response.sendRedirect(refreshTokenPath);
    //                             }))
    //             .addFilterBefore(accessTokenAuthenticationFilter,
    //                     UsernamePasswordAuthenticationFilter.class)
    //             .oauth2Login(oauth2 -> oauth2
    //                     .redirectionEndpoint(endpoint -> endpoint
    //                             .baseUri("/auth/oauth2/callback/**"))
    //                     .userInfoEndpoint(endpoint -> endpoint
    //                             .userService(oAuth2JoinService))
    //                     .successHandler(successHandler))
    //             .logout(logoutConfig -> logoutConfig
    //                     .logoutUrl("/auth/logout")
    //                     .logoutSuccessUrl("/")
    //                     .deleteCookies(refreshTokenName, accessTokenName, "LoginChecker")
    //                     .addLogoutHandler(logoutHandler));
    //     return http.build();
    // }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {

    //     // refresh token
    //     CorsConfiguration config1 = new CorsConfiguration();
    //     config1.setAllowedOrigins(Arrays.asList(DOMAIN_AND_PORT));
    //     config1.setAllowedMethods(Arrays.asList(GET, POST, PUT, DELETE, PATCH));
    //     config1.setAllowedHeaders(Arrays.asList("*"));
    //     config1.setAllowCredentials(true);
    //     config1.setExposedHeaders(Arrays.asList(accessTokenName, HttpHeaders.SET_COOKIE)); // AccessToken 헤더 노출

    //     UrlBasedCorsConfigurationSource source1 = new UrlBasedCorsConfigurationSource();
    //     source1.registerCorsConfiguration(refreshTokenPath, config1);
    //     source1.registerCorsConfiguration("/", config1);

    //     // api require authentication -> use access token
    //     CorsConfiguration config2 = new CorsConfiguration();
    //     config2.setAllowedOrigins(Arrays.asList(DOMAIN_AND_PORT));
    //     config2.setAllowedMethods(Arrays.asList(GET, POST, PUT, DELETE, PATCH));
    //     config2.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, "*"));
    //     config2.setAllowCredentials(false);

    //     UrlBasedCorsConfigurationSource source2 = new UrlBasedCorsConfigurationSource();
    //     source2.registerCorsConfiguration("/api/**", config2);

    //     return source1;
    // }
}
