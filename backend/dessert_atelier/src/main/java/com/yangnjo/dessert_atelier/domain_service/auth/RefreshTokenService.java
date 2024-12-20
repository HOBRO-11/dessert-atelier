package com.yangnjo.dessert_atelier.domain_service.auth;

import com.yangnjo.dessert_atelier.common.token_util.TokenHeader;

import jakarta.servlet.http.HttpServletRequest;

public interface RefreshTokenService {

    Long validateTokenAndGetMemberId(String refreshTokenString, HttpServletRequest request);

    TokenHeader putRefreshToken(Long memberId, HttpServletRequest request);

    void deleteRefreshToken(Long memberId);

    void expiredRefreshToken(Long memberId);

}