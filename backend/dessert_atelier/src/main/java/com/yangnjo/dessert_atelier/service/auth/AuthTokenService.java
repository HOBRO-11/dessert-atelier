package com.yangnjo.dessert_atelier.service.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.token_util.AccessTokenProvider;
import com.yangnjo.dessert_atelier.common.token_util.RefreshTokenProvider;
import com.yangnjo.dessert_atelier.common.token_util.TokenHeader;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain_service.auth.RefreshTokenService;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthTokenService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;

    public Long validateTokenAndGetMemberId(String refreshTokenString, HttpServletRequest request) {
        return refreshTokenService.validateTokenAndGetMemberId(refreshTokenString, request);
    }

    public List<TokenHeader> putAndGetRefreshTokenHeaders(Long memberId, HttpServletRequest request) {
        TokenHeader tokenHeader = refreshTokenService.putRefreshToken(memberId, request);
        return List.of(tokenHeader,
                refreshTokenProvider.getLoginCheckHeader());
    }

    public TokenHeader getAccessTokenHeaders(Long memberId) {
        Member member = findMemberById(memberId);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getMemberRole().getRole());
        String accessToken = accessTokenProvider.create(String.valueOf(memberId), claims, null);

        return accessTokenProvider.getTokenHeader(accessToken);
    }

    public void expiredRefreshToken(String oldRefreshTokenString, HttpServletRequest request) {
        Long memberId = refreshTokenService.validateTokenAndGetMemberId(oldRefreshTokenString, request);
        refreshTokenService.expiredRefreshToken(memberId);
    }

    public List<TokenHeader> getClearTokenHeaders() {
        List<TokenHeader> headers = new ArrayList<>();
        headers.addAll(accessTokenProvider.clearTokenHeader());
        headers.addAll(refreshTokenProvider.clearTokenHeader());
        return headers;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}
