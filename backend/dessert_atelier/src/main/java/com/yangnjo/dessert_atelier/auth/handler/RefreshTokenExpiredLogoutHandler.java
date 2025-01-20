package com.yangnjo.dessert_atelier.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.yangnjo.dessert_atelier.auth.domain.domain_service.RefreshTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenExpiredLogoutHandler implements LogoutHandler {
    private final RefreshTokenService refreshTokenService;
    private final LoginCheckHandler loginCheckHandler;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("refreshToken")) {
                        Long memberId = refreshTokenService.validateTokenAndGetMemberId(cookie.getValue(), request);
                        refreshTokenService.expiredRefreshToken(memberId);
                        loginCheckHandler.logout(memberId);
                        break;
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                response.sendRedirect("/");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
