package com.yangnjo.dessert_atelier.member.controller.form.request;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressUpdateForm {
    Long addressId;
    String naming;
    Address address;
    boolean isDefault;

    public MemberAddressUpdateDto toDto() {
        return new MemberAddressUpdateDto(addressId, naming, address, isDefault);
    }
}
