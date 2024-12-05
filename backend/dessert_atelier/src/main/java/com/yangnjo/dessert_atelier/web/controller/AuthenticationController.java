package com.yangnjo.dessert_atelier.web.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yangnjo.dessert_atelier.provider.TokenHeader;
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
            Long memberId = authTokenService.validateTokenAndGetMemberId(refreshToken, request);
            List<TokenHeader> tokenHeaders = authTokenService.putAndGetRefreshTokenHeaders(memberId, request);
            HttpHeaders headers = new HttpHeaders();
            tokenHeaders.forEach(th -> th.setHeader(headers));
            headers.add(HttpHeaders.LOCATION, "/");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
