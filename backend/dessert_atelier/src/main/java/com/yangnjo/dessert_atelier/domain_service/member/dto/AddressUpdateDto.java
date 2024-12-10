package com.yangnjo.dessert_atelier.domain_service.member.dto;

import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressUpdateDto {
    Long addressId;
    String naming;
    Long memberId;
    Destination destination;
    boolean isDefault;
}
