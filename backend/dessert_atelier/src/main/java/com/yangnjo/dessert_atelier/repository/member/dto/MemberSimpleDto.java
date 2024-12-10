package com.yangnjo.dessert_atelier.repository.member.dto;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_model.member.QMember;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSimpleDto {

    private Long id;
    private String name;
    private MemberStatus memberStatus;

    public static Expression<MemberSimpleDto> asDto() {
        QMember member = QMember.member;
        return Projections.constructor(MemberSimpleDto.class,
                member.id,
                member.name,
                member.memberStatus);
    }
}