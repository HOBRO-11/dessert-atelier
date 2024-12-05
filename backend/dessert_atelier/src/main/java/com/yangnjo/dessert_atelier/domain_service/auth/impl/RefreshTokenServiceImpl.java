package com.yangnjo.dessert_atelier.domain_service.auth.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.auth.RefreshToken;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain_service.auth.RefreshTokenService;
import com.yangnjo.dessert_atelier.domain_service.auth.exception.RefreshTokenExpiredException;
import com.yangnjo.dessert_atelier.domain_service.auth.exception.RefreshTokenNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.auth.exception.RefreshTokenNotMatchedException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.provider.RefreshTokenProvider;
import com.yangnjo.dessert_atelier.provider.TokenHeader;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.RefreshTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

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
        Member member = findMemberById(memberId);
        String newToken = refreshTokenProvider.create(memberId.toString(), null, request);
        String signature = refreshTokenProvider.getSignature(newToken);
        LocalDateTime expDateTime = refreshTokenProvider.getExpiredDateTime(newToken);

        RefreshToken savedToken = findRefreshTokenByMemberId(memberId);

        if (savedToken != null) {
            savedToken.updateToken(signature, expDateTime);
        } else {
            refreshTokenRepository.save(new RefreshToken(member, signature, expDateTime));
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
        findMemberById(memberId);
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
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByMemberId(memberId).orElse(null);
        // RefreshToken refreshTokenEntity =
        // refreshTokenRepository.findByMemberId(memberId).orElseThrow(RefreshTokenNotFoundException::new);
        return refreshTokenEntity;
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return member;
    }
}
