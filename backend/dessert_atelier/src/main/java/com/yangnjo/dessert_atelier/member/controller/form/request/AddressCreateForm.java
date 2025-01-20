package com.yangnjo.dessert_atelier.member.controller.form.request;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressCreateForm {
    String naming;
    Address address;
    boolean isDefault;

    public MemberAddressCreateDto toDto() {
        return new MemberAddressCreateDto(naming, address, isDefault);
    }
}
