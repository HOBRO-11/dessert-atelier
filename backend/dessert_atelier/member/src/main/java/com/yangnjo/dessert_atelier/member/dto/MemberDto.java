package com.yangnjo.dessert_atelier.member.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberOrigin;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberRole;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;
import com.yangnjo.dessert_atelier.member.domain.entity.QMember;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {

    private Long memberId;
    private String email;
    private String name;
    private String phone;
    private MemberOrigin memberOrigin;
    private MemberRole memberRole;
    private MemberStatus status;

    public static Expression<MemberDto> asDto() {
        QMember member = QMember.member;
        return Projections.constructor(MemberDto.class,
                member.id,
                member.email,
                member.name,
                member.phone,
                member.memberOrigin,
                member.memberRole,
                member.status);
    }
}