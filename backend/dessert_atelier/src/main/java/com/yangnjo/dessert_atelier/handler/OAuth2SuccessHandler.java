package com.yangnjo.dessert_atelier.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.auth.RefreshToken;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain_service.auth.RefreshTokenService;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.provider.AccessTokenProvider;
import com.yangnjo.dessert_atelier.provider.RefreshTokenProvider;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.RefreshTokenRepository;
import com.yangnjo.dessert_atelier.service.auth.OAuth2MemberInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepo;
    private final RefreshTokenService refreshTokenService;
    private final MemberRepository memberRepository;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AccessTokenProvider accessTokenProvider;
    private final LoginCheckHandler loginCheckHandler;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2MemberInfo memberInfo = (OAuth2MemberInfo) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(memberInfo.getEmail()).orElseThrow(MemberNotFoundException::new);

        expirePrevRefreshToken(request, member);
        setAccessTokenHeader(response, member);
        setRefreshTokenHeader(request, response, member);

        // 응답 헤더 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        getRedirectStrategy().sendRedirect(request, response, "/");
    }

    private void expirePrevRefreshToken(HttpServletRequest request, Member member) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                try {
                    Long jwtMemberId = refreshTokenService.validateTokenAndGetMemberId(cookie.getValue(), request);

                    if (jwtMemberId == member.getId()) {
                        break;
                    }

                    refreshTokenService.expiredRefreshToken(jwtMemberId);
                    loginCheckHandler.logout(jwtMemberId);
                    break;
                } catch (Exception e) {
                }
            }
        }
    }

    private void setRefreshTokenHeader(HttpServletRequest request, HttpServletResponse response, Member member) {
        String refreshTokenString = refreshTokenProvider.create(member.getId().toString(), request);
        refreshTokenRepo.findById(member.getId())
                .ifPresentOrElse(
                        rt -> rt.updateToken(refreshTokenProvider.getSignature(refreshTokenString),
                                refreshTokenProvider.getExpiredDuration()),
                        () -> refreshTokenRepo.save(
                                new RefreshToken(member,
                                        refreshTokenProvider.getSignature(refreshTokenString),
                                        refreshTokenProvider.getExpiredDuration())));

        Map<String, String> refreshTokenHeader = refreshTokenProvider.getRefreshTokenCookieHeader(refreshTokenString);
        refreshTokenHeader.forEach((k, v) -> response.setHeader(k, v));
        loginCheckHandler.login(member.getId());
    }

    private void setAccessTokenHeader(HttpServletResponse response, Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getMemberRole().getRole());
        String accessTokenString = accessTokenProvider.create(member.getId().toString(), claims);
        Map<String, String> accessTokenHeader = accessTokenProvider.setAccessTokenHeader(accessTokenString);
        accessTokenHeader.forEach((k, v) -> response.setHeader(k, v));
    }

}
