package com.yangnjo.dessert_atelier.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yangnjo.dessert_atelier.service.auth.AuthTokenService;
import com.yangnjo.dessert_atelier.service.auth.exception.TokenNotFoundException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthTokenService authTokenService;

    @PostMapping("/refresh-token")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshTokeString = null;
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh-token")) {
                    refreshTokeString = cookie.getValue();
                }
            }
        }

        if (refreshTokeString != null) {
            authTokenService.refresh(request, response, refreshTokeString);
        }

        throw new TokenNotFoundException();
    }

}
