package com.yangnjo.dessert_atelier.domain_service.auth;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.auth.RefreshToken;

public interface RefreshTokenService {

    RefreshToken findRefreshToken(Long memberId);

    Long createRefreshToken(Long memberId, String refreshToken, LocalDateTime expiredDate);

    void updateRefreshToken(Long memberId, String refreshToken, LocalDateTime expiredDate);

    void validateRefreshToken(Long memberId, String refreshToken, LocalDateTime expectedExpiredDate);

    void deleteRefreshToken(Long memberId);

    void expiredRefreshToken(Long memberId);

}