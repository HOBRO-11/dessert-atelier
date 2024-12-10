package com.yangnjo.dessert_atelier.repository.member.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.member.QAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressSimpleDto {

    private Long id;
    private Long memberId;
    private String naming;
    private boolean isDefault;

    public static Expression<AddressSimpleDto> asDto() {
        QAddress address = QAddress.address;
        return Projections.constructor(AddressSimpleDto.class,
                address.id,
                address.member.id,
                address.naming,
                address.isDefault);
    }
}
