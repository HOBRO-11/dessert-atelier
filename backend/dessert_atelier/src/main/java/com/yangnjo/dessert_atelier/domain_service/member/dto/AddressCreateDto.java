package com.yangnjo.dessert_atelier.domain_service.member.dto;

import com.yangnjo.dessert_atelier.domain_model.member.Address;
import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressCreateDto {
    String naming;
    Destination destination;
    boolean isDefault;

    public Address toEntity() {
        return new Address(naming, destination.getPostCode(), destination.getDetailAddress(), destination.getReceiver(),
                destination.getPhone(), isDefault);
    }
}
