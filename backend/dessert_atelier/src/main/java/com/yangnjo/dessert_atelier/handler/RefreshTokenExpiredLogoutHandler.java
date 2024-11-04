package com.yangnjo.dessert_atelier.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.yangnjo.dessert_atelier.domain_service.auth.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenExpiredLogoutHandler implements LogoutHandler {
    private final RefreshTokenService refreshTokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        refreshTokenService.expiredRefreshToken(memberId);
    }

}
