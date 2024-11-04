package com.yangnjo.dessert_atelier.service.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;

import lombok.Getter;
import lombok.Setter;

public class OAuth2MemberInfo implements OAuth2User {
    private static final String GOOGLE = "google";
    private static final String NAVER = "naver";


    private String nickname;
    @Getter
    private String email;
    @Setter
    private MemberRole memberRole;
    private MemberOrigin origin;

    public OAuth2MemberInfo(String nickname, String email, MemberOrigin origin) {
        this.nickname = nickname;
        this.email = email;
        this.origin = origin;
    }

    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("nickname", nickname, "email", email, "origin", origin.name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memberRole.getRole()));
    }

    public static OAuth2MemberInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case GOOGLE -> ofGoogle(attributes);
            case NAVER -> ofNaver(attributes);
            default -> throw new RuntimeException();
        };
    }

    private static OAuth2MemberInfo ofGoogle(Map<String, Object> attributes) {
        return new OAuth2MemberInfo((String) attributes.get("name"), (String) attributes.get("email"),
                MemberOrigin.GOOGLE);
    }

    private static OAuth2MemberInfo ofNaver(Map<String, Object> attributes) {
        return new OAuth2MemberInfo((String) attributes.get("nickname"), (String) attributes.get("email"),
                MemberOrigin.NAVER);
    }

    public Member toEntity() {
        Member entity = switch (origin.name().toLowerCase()) {
            case GOOGLE -> new Member(email, null, nickname, null, memberRole, MemberOrigin.GOOGLE);
            case NAVER -> new Member(email, null, nickname, null, memberRole, MemberOrigin.NAVER);
            default -> throw new RuntimeException();
        };
        return entity;
    }

}
