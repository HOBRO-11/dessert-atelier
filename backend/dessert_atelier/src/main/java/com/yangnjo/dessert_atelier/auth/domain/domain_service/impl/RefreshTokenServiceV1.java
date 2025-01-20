package com.yangnjo.dessert_atelier.auth.domain.domain_service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.auth.domain.domain_service.RefreshTokenService;
import com.yangnjo.dessert_atelier.auth.domain.entity.RefreshToken;
import com.yangnjo.dessert_atelier.auth.domain.repository.RefreshTokenRepository;
import com.yangnjo.dessert_atelier.auth.exception.RefreshTokenExpiredException;
import com.yangnjo.dessert_atelier.auth.exception.RefreshTokenNotFoundException;
import com.yangnjo.dessert_atelier.auth.exception.RefreshTokenNotMatchedException;
import com.yangnjo.dessert_atelier.auth.util.token.RefreshTokenProvider;
import com.yangnjo.dessert_atelier.auth.util.token.TokenHeader;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceV1 implements RefreshTokenService {

    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional(readOnly = true)
    public Long validateTokenAndGetMemberId(String refreshTokenString, HttpServletRequest request) {
        String memberIdString = refreshTokenProvider.validate(refreshTokenString, request).getSubject();
        long memberId = Long.parseLong(memberIdString);

        validateToken(refreshTokenString, memberId);

        return memberId;
    }

    @Override
    public TokenHeader putRefreshToken(Long memberId, HttpServletRequest request) {
        String newToken = refreshTokenProvider.create(memberId.toString(), null, request);
        String signature = refreshTokenProvider.getSignature(newToken);
        LocalDateTime expDateTime = refreshTokenProvider.getExpiredDateTime(newToken);

        RefreshToken savedToken = findRefreshTokenByMemberId(memberId);

        if (savedToken != null) {
            savedToken.updateToken(signature, expDateTime);
        } else {
            refreshTokenRepository.save(new RefreshToken(memberId, signature, expDateTime));
        }

        return refreshTokenProvider.getTokenHeader(newToken);
    }

    @Override
    public void deleteRefreshToken(Long memberId) {
        Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findByMemberId(memberId);
        refreshTokenEntity.ifPresent(refreshTokenRepository::delete);
    }

    @Override
    public void expiredRefreshToken(Long memberId) {
        RefreshToken refreshToken = findRefreshTokenByMemberId(memberId);
        refreshToken.updateToken(null, LocalDateTime.now());
    }

    private void validateToken(String refreshTokenString, long memberId) {
        RefreshToken savedRefreshToken = findRefreshTokenByMemberId(memberId);
        if (savedRefreshToken == null) {
            throw new RefreshTokenNotFoundException();
        }
        LocalDateTime expectedExpiredDate = refreshTokenProvider.getExpiredDateTime(refreshTokenString);
        String oldSignature = savedRefreshToken.getRefreshTokenSignature();
        String signature = refreshTokenProvider.getSignature(refreshTokenString);

        if (savedRefreshToken.getExpiredDate().isBefore(expectedExpiredDate)) {
            throw new RefreshTokenExpiredException();
        }

        if (oldSignature.equals(signature) == false) {
            throw new RefreshTokenNotMatchedException();
        }
    }

    private RefreshToken findRefreshTokenByMemberId(Long memberId) {
        return refreshTokenRepository.findByMemberId(memberId).orElse(null);
    }

}
