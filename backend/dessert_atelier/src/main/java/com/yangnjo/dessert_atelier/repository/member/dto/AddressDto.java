package com.yangnjo.dessert_atelier.repository.member.dto;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.member.QAddress;
import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDto {

    private Long id;
    private Long memberId;
    private String naming;
    private Destination destination;
    private boolean isDefault;

    public static Expression<AddressDto> asDto() {
        QAddress address = QAddress.address;
        return Projections.constructor(AddressDto.class,
                address.id,
                address.member.id,
                address.naming,
                address.destination,
                address.isDefault);
    }
}
