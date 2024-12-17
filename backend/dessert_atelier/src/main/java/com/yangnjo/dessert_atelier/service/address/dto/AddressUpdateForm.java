package com.yangnjo.dessert_atelier.service.address.dto;

import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressUpdateDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddressUpdateForm {
    Long addressId;
    String naming;
    Destination Destination;

    public AddressUpdateDto toDto(Long memberId) {
        return new AddressUpdateDto(addressId, naming, memberId, Destination, false);
    }
}
