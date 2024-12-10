package com.yangnjo.dessert_atelier.repository.member.dto;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain_model.member.MemberRole;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_model.member.QMember;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private MemberOrigin memberOrigin;
    private MemberRole memberRole;
    private MemberStatus memberStatus;

    public static Expression<MemberDto> asDto() {
        QMember member = QMember.member;
        return Projections.constructor(MemberDto.class,
                member.id,
                member.email,
                member.name,
                member.phone,
                member.memberOrigin,
                member.memberRole,
                member.memberStatus);
    }
}