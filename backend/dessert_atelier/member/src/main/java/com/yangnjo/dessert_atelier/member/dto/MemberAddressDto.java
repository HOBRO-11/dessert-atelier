package com.yangnjo.dessert_atelier.member.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.member.domain.entity.QMemberAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberAddressDto {

    private Long addressId;
    private Long memberId;
    private String naming;
    private Address destination;
    private boolean isDefault;

    public static Expression<MemberAddressDto> asDto() {
        QMemberAddress memberAddress = QMemberAddress.memberAddress;
        return Projections.constructor(MemberAddressDto.class,
                memberAddress.id,
                memberAddress.member.id,
                memberAddress.naming,
                memberAddress.address,
                memberAddress.isDefault);
    }
}
