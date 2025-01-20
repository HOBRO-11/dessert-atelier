package com.yangnjo.dessert_atelier.member.dto;

import com.yangnjo.dessert_atelier.member.domain.entity.Member;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberOrigin;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberRole;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateDto {
    String email;
    String name;
    MemberOrigin origin;

    public Member toEntityForMember() {
        return Member.builder()
                .email(email)
                .name(name)
                .memberRole(MemberRole.MEMBER)
                .memberOrigin(origin)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public Member toEntityForAdmin() {
        return Member.builder()
                .email(email)
                .name(name)
                .memberRole(MemberRole.ADMIN)
                .memberOrigin(origin)
                .status(MemberStatus.NON_QUALIFIED)
                .build();
    }

    public Member toEntityForOwner() {
        return Member.builder()
                .email(email)
                .name(name)
                .memberRole(MemberRole.OWNER)
                .memberOrigin(origin)
                .status(MemberStatus.ACTIVE)
                .build();
    }
}
