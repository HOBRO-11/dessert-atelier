package com.yangnjo.dessert_atelier.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yangnjo.dessert_atelier.handler.LoginCheckHandler;
import com.yangnjo.dessert_atelier.provider.AccessTokenProvider;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenProvider accessTokenProvider;
    private final LoginCheckHandler loginCheckHandler;
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String accessToken = request.getHeader("access-token");
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims validate = accessTokenProvider.validate(accessToken, null);
            Long memberId = Long.parseLong(validate.getSubject());

            if(loginCheckHandler.checkLogin(memberId) == false){
                filterChain.doFilter(request, response);
                return;
            }

            String memberRole = validate.get("role", String.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + memberRole));

            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, null,
                    authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

            securityContext.setAuthentication(authenticationToken);

        } catch (Exception e) {
            log.error("인증 처리 중 에러 발생: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

}
