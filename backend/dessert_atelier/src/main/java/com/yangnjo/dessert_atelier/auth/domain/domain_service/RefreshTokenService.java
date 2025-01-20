package com.yangnjo.dessert_atelier.auth.domain.domain_service;

import com.yangnjo.dessert_atelier.auth.util.token.TokenHeader;

import jakarta.servlet.http.HttpServletRequest;

public interface RefreshTokenService {

    Long validateTokenAndGetMemberId(String refreshTokenString, HttpServletRequest request);

    TokenHeader putRefreshToken(Long memberId, HttpServletRequest request);

    void deleteRefreshToken(Long memberId);

    void expiredRefreshToken(Long memberId);

}