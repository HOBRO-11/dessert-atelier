package com.yangnjo.dessert_atelier.member.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;
import com.yangnjo.dessert_atelier.member.domain.entity.QMember;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSimpleDto {
    private Long memberId;
    private String name;
    private MemberStatus status;

    public static Expression<MemberSimpleDto> asDto() {
        QMember member = QMember.member;
        return Projections.constructor(MemberSimpleDto.class,
                member.id,
                member.name,
                member.status);
    }
}