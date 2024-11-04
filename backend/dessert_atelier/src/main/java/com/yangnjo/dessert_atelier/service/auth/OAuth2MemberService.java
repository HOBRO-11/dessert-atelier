package com.yangnjo.dessert_atelier.service.auth;

import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberAlreadyExistException;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

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
