package com.yangnjo.dessert_atelier.web.controller;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yangnjo.dessert_atelier.service.auth.AuthTokenService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthTokenService authTokenService;

    @GetMapping("/auth/refresh")
    public ResponseEntity<Void> reissueRefreshToken(
            HttpServletRequest request,
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        try {
            Map<String, String> refreshTokenHeaders = authTokenService.getRefreshTokenHeaders(request, refreshToken);
            HttpHeaders headers = new HttpHeaders();
            refreshTokenHeaders.forEach((k, v) -> headers.add(k, v));
            headers.add(HttpHeaders.LOCATION, "/");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/auth/login");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<Void> reissueToken(HttpServletRequest request,
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        try {
            Map<String, String> refreshTokenHeaders = authTokenService.getRefreshTokenHeaders(request, refreshToken);
            Long memberId = authTokenService.getMemberId(request, refreshToken);
            Map<String, String> accessTokenHeaders = authTokenService.getAccessTokenHeaders(memberId);

            HttpHeaders headers = new HttpHeaders();
            refreshTokenHeaders.forEach((k, v) -> headers.add(k, v));
            accessTokenHeaders.forEach((k, v) -> headers.add(k, v));
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
