package com.yangnjo.dessert_atelier.member.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.member.domain.entity.QMemberAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberAddressSimpleDto {

    private Long addressId;
    private Long memberId;
    private String naming;
    private boolean isDefault;

    public static Expression<MemberAddressSimpleDto> asDto() {
        QMemberAddress memberAddress = QMemberAddress.memberAddress;
        return Projections.constructor(MemberAddressSimpleDto.class,
                memberAddress.id,
                memberAddress.member.id,
                memberAddress.naming,
                memberAddress.isDefault);
    }
}
