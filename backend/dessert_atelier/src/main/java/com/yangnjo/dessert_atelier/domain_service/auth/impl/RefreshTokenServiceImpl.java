package com.yangnjo.dessert_atelier.domain_service.auth.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.auth.RefreshToken;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain_service.auth.RefreshTokenService;
import com.yangnjo.dessert_atelier.domain_service.auth.exception.RefreshTokenAlreadyExistsException;
import com.yangnjo.dessert_atelier.domain_service.auth.exception.RefreshTokenExpiredException;
import com.yangnjo.dessert_atelier.domain_service.auth.exception.RefreshTokenNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.provider.RefreshTokenProvider;
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
    public RefreshToken findRefreshToken(Long memberId) {
        findMemberById(memberId);
        return findRefreshTokenByMemberId(memberId);
    }

    @Override
    public Long validateTokenAndGetMemberId(String refreshTokenString, HttpServletRequest request) {
        String memberIdString = refreshTokenProvider.validate(refreshTokenString, request);

        long memberId = Long.parseLong(memberIdString);
        RefreshToken oldRefreshToken = refreshTokenRepository.findById(memberId)
                .orElseThrow(RefreshTokenNotFoundException::new);

        String oldSignature = oldRefreshToken.getRefreshTokenSignature();
        String signature = refreshTokenProvider.getSignature(refreshTokenString);

        if (oldSignature.equals(signature) == false) {
            throw new RefreshTokenNotFoundException();
        }

        return memberId;
    }

    @Override
    public Long createRefreshToken(Long memberId, String refreshToken, LocalDateTime expiredDate) {
        Member member = findMemberById(memberId);
        Optional<RefreshToken> optionRefreshToken = refreshTokenRepository.findByMemberId(memberId);
        if (optionRefreshToken.isPresent()) {
            throw new RefreshTokenAlreadyExistsException();
        }

        String signature = refreshTokenProvider.getSignature(refreshToken);
        return refreshTokenRepository.save(new RefreshToken(member, signature, expiredDate)).getId();
    }

    @Override
    public void updateRefreshToken(Long memberId, String refreshToken, LocalDateTime expiredDate) {
        RefreshToken refreshTokenEntity = findRefreshTokenByMemberId(memberId);
        String signature = refreshTokenProvider.getSignature(refreshToken);
        refreshTokenEntity.updateToken(signature, expiredDate);
    }

    @Override
    public void validateRefreshToken(Long memberId, String refreshToken, LocalDateTime expectedExpiredDate) {
        RefreshToken refreshTokenEntity = findRefreshTokenByMemberId(memberId);
        String signature = refreshTokenProvider.getSignature(refreshToken);
        if (refreshTokenEntity.getRefreshTokenSignature().equals(signature) == false) {
            throw new RefreshTokenNotFoundException();
        }

        if (refreshTokenEntity.getExpiredDate().isBefore(expectedExpiredDate)) {
            throw new RefreshTokenExpiredException();
        }
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

    private RefreshToken findRefreshTokenByMemberId(Long memberId) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(RefreshTokenNotFoundException::new);
        return refreshTokenEntity;
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        return member;
    }

}
