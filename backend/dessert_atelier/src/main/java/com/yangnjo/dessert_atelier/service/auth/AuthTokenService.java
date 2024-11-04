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
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthTokenService {

    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberQueryService memberQueryService;

    public void refresh(HttpServletRequest request, HttpServletResponse response, String refreshTokenString) {

        Long memberId =  Long.parseLong(refreshTokenProvider.validate(refreshTokenString, request));  
        MemberSimpleDto memberDto = memberQueryService.getMemberById(memberId);

        LocalDateTime expiredDateTime = refreshTokenProvider.getExpiredDateTime(refreshTokenString);
        refreshTokenService.validateRefreshToken(memberId, refreshTokenString, expiredDateTime);

        String newRefreshToken = refreshTokenProvider.create(String.valueOf(memberId), request);
        LocalDateTime newExpDateTime = refreshTokenProvider.getExpiredDateTime(newRefreshToken);
        refreshTokenService.updateRefreshToken(memberId, newRefreshToken, newExpDateTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", memberDto.getMemberRole().getRole());
        String accessToken = accessTokenProvider.create(String.valueOf(memberId), claims);
        accessTokenProvider.setAccessToken(response, accessToken);

        refreshTokenProvider.setRefreshTokenToCookie(response, newRefreshToken);
    }

}
