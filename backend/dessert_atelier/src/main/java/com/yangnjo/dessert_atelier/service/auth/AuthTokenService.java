package com.yangnjo.dessert_atelier.service.auth;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.domain_service.auth.RefreshTokenService;
import com.yangnjo.dessert_atelier.domain_service.member.MemberQueryService;
import com.yangnjo.dessert_atelier.provider.AccessTokenProvider;
import com.yangnjo.dessert_atelier.provider.RefreshTokenProvider;
import com.yangnjo.dessert_atelier.repository.dto.MemberSimpleDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberQueryService memberQueryService;

    public Map<String, String> getRefreshTokenHeaders(HttpServletRequest request, String oldRefreshTokenString) {
        if (oldRefreshTokenString == null) {
            throw new IllegalArgumentException("Refresh token is null");
        }
        Long memberId = Long.parseLong(refreshTokenProvider.validate(oldRefreshTokenString, request));

        LocalDateTime expiredDateTime = refreshTokenProvider.getExpiredDateTime(oldRefreshTokenString);
        refreshTokenService.validateRefreshToken(memberId, oldRefreshTokenString, expiredDateTime);

        String newRefreshToken = refreshTokenProvider.create(String.valueOf(memberId), request);
        LocalDateTime newExpDateTime = refreshTokenProvider.getExpiredDateTime(newRefreshToken);
        refreshTokenService.updateRefreshToken(memberId, newRefreshToken, newExpDateTime);

        return refreshTokenProvider.getRefreshTokenCookieHeader(newRefreshToken);
    }

    public Map<String, String> getAccessTokenHeaders(Long memberId) {
        MemberSimpleDto memberDto = memberQueryService.getMemberById(memberId);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", memberDto.getMemberRole().getRole());
        String accessToken = accessTokenProvider.create(String.valueOf(memberId), claims);

        return accessTokenProvider.setAccessTokenHeader(accessToken);
    }

    public Long getMemberId(HttpServletRequest request, String oldRefreshTokenString) {
        return Long.parseLong(refreshTokenProvider.validate(oldRefreshTokenString, request));
    }

}
