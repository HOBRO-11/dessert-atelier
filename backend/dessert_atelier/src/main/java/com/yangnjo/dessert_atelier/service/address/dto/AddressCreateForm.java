package com.yangnjo.dessert_atelier.service.address.dto;

import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressCreateForm {
    String naming;
    Destination Destination;
    boolean isDefault;

    public AddressCreateDto toDto() {
        return new AddressCreateDto(naming, Destination, isDefault);
    }
}
