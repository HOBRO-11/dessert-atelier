package com.yangnjo.dessert_atelier.handler;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.yangnjo.dessert_atelier.common.token_util.TokenHeader;
import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.repository.member.MemberRepository;
import com.yangnjo.dessert_atelier.service.auth.AuthTokenService;
import com.yangnjo.dessert_atelier.service.auth.OAuth2MemberInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final AuthTokenService authTokenService;
    private final LoginCheckHandler loginCheckHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        clearTokenHeaders(response);

        OAuth2MemberInfo memberInfo = (OAuth2MemberInfo) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(memberInfo.getEmail()).orElseThrow(MemberNotFoundException::new);

        try {
            expirePrevRefreshToken(request, member);
            setRefreshTokenHeader(request, response, member);
            setAccessTokenHeader(response, member);
            loginCheckHandler.login(member.getId());
        } catch (Exception e) {
        }
        
        getRedirectStrategy().sendRedirect(request, response, "/");
    }

    private void expirePrevRefreshToken(HttpServletRequest request, Member member) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                try {
                    Long jwtMemberId = authTokenService.validateTokenAndGetMemberId(cookie.getValue(), request);

                    if (jwtMemberId == member.getId()) {
                        break;
                    }

                    authTokenService.expiredRefreshToken(cookie.getValue(), request);
                    loginCheckHandler.logout(jwtMemberId);
                    break;
                } catch (Exception e) {
                }
            }
        }
    }

    private void clearTokenHeaders(HttpServletResponse response) {
        authTokenService.getClearTokenHeaders().forEach(th -> th.addCookie(response));
    }

    private void setRefreshTokenHeader(HttpServletRequest request, HttpServletResponse response, Member member) {
        try {
            List<TokenHeader> tokenHeaders = authTokenService.putAndGetRefreshTokenHeaders(member.getId(), request);
            tokenHeaders.forEach(th -> th.addCookie(response));
        } catch (Exception e) {
            throw new IllegalArgumentException("cant issue refresh token", e);
        }
    }

    private void setAccessTokenHeader(HttpServletResponse response, Member member) {
        TokenHeader tokenHeader = authTokenService.getAccessTokenHeaders(member.getId());
        tokenHeader.addCookie(response);
    }

}
