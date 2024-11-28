package com.yangnjo.dessert_atelier.domain_service.auth;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.auth.RefreshToken;

import jakarta.servlet.http.HttpServletRequest;

public interface RefreshTokenService {

    public Long validateTokenAndGetMemberId(String refreshTokenString, HttpServletRequest request);

    RefreshToken findRefreshToken(Long memberId);

    Long createRefreshToken(Long memberId, String refreshToken, LocalDateTime expiredDate);

    void updateRefreshToken(Long memberId, String refreshToken, LocalDateTime expiredDate);

    void validateRefreshToken(Long memberId, String refreshToken, LocalDateTime expectedExpiredDate);

    void deleteRefreshToken(Long memberId);

    void expiredRefreshToken(Long memberId);

}