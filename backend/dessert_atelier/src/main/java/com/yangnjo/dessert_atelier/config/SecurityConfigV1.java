package com.yangnjo.dessert_atelier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.filter.AccessTokenAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfigV1 {

    private final DefaultOAuth2UserService oAuth2JoinService;
    private final AccessTokenAuthenticationFilter accessTokenAuthenticationFilter;
    private final SimpleUrlAuthenticationSuccessHandler successHandler;
    private final LogoutHandler refreshTokenLogoutHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/favicon.ico",
                "/error");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .httpBasic(HttpBasicConfigurer::disable)
                // .formLogin(formLoginConfig -> formLoginConfig.disable())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/login/**", "/css/**", "/js/**", "/images/**", "/oauth2/**",
                                "/refresh-token")
                        .permitAll()
                        .requestMatchers("/api/**").hasRole(MemberRole.MEMBER.name())
                        .anyRequest().authenticated())
                .addFilterBefore(accessTokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/callback/**"))
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2JoinService))
                        .successHandler(successHandler))
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("refresh_token")
                        .addLogoutHandler(refreshTokenLogoutHandler));
        return http.build();
    }

}
