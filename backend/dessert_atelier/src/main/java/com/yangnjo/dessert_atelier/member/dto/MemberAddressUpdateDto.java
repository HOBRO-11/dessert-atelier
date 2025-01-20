package com.yangnjo.dessert_atelier.member.dto;

import com.yangnjo.dessert_atelier.commons.value_type.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberAddressUpdateDto {
    Long addressId;
    String naming;
    Address address;
    boolean isDefault;
}
