package com.yangnjo.dessert_atelier.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.auth.RefreshToken;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.provider.AccessTokenProvider;
import com.yangnjo.dessert_atelier.provider.RefreshTokenProvider;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.RefreshTokenRepository;
import com.yangnjo.dessert_atelier.service.auth.OAuth2MemberInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepo;
    private final MemberRepository memberRepository;
    private final RefreshTokenProvider refreshTokenProvider;
    private final AccessTokenProvider accessTokenProvider;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String refreshTokenString = null;
        LocalDateTime expDate = null;

        OAuth2MemberInfo memberInfo = (OAuth2MemberInfo) authentication.getPrincipal();

        Member member = memberRepository.findByEmail(memberInfo.getEmail()).orElseThrow(MemberNotFoundException::new);
        String memberId = String.valueOf(member.getId());

        Optional<RefreshToken> refreshToken = refreshTokenRepo.findByMemberId(member.getId());
        refreshTokenString = refreshTokenProvider.create(memberId, request);

        if (refreshToken.isPresent()) {
            refreshToken.get().updateToken(refreshTokenProvider.getSignature(refreshTokenString), expDate);
        }else{
            expDate = refreshTokenProvider.getExpiredDuration();
            refreshTokenRepo.save(new RefreshToken(member, refreshTokenProvider.getSignature(refreshTokenString), expDate));
        }

        refreshTokenProvider.setRefreshTokenToCookie(response, refreshTokenString);
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getMemberRole().getRole());
        String accessTokenString = accessTokenProvider.create(memberId, claims);
        accessTokenProvider.setAccessToken(response, accessTokenString);

        response.flushBuffer();

        getRedirectStrategy().sendRedirect(request, response, "/");
    }

}
