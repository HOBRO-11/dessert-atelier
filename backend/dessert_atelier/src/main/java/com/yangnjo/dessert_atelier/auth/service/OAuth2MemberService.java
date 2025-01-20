package com.yangnjo.dessert_atelier.auth.service;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.auth.dto.OAuth2MemberInfo;
import com.yangnjo.dessert_atelier.member.domain.entity.Member;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberOrigin;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberRole;
import com.yangnjo.dessert_atelier.member.domain.repository.MemberRepository;
import com.yangnjo.dessert_atelier.member.exception.MemberAlreadyExistException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        System.out.println(Thread.currentThread().getName());

        OAuth2User oAuth2User = super.loadUser(request);
        String registrationId = request.getClientRegistration().getRegistrationId();
        OAuth2MemberInfo memberInfo = OAuth2MemberInfo.of(registrationId, oAuth2User.getAttributes());

        Optional<Member> optionalMember = memberRepository.findByEmail(memberInfo.getEmail());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            MemberOrigin memberOrigin = member.getMemberOrigin();
            String memberOriginLowerCase = memberOrigin.name().toLowerCase();

            if (memberOriginLowerCase.equals(registrationId)) {
                memberInfo.setMemberRole(member.getMemberRole());
                return memberInfo;
            }

            throw new MemberAlreadyExistException(memberOrigin);
        }

        memberInfo.setMemberRole(MemberRole.MEMBER);
        Member entity = memberInfo.toEntity();
        memberRepository.save(entity);

        return memberInfo;
    }

}
