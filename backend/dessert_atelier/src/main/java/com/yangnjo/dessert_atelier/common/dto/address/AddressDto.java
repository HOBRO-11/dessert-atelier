package com.yangnjo.dessert_atelier.common.dto.address;

import com.yangnjo.dessert_atelier.db.entity.Address;
import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDto {

    private Long id;

    private String naming;

    private String postCode;

    private String detailAddress;

    private String receiver;

    private int phone;

    private boolean isDefault;

    public static AddressDto toDto(Address address) {
        return new AddressDto(address.getId(), address.getNaming(), address.getPostCode(), address.getDetailAddress(),
                address.getReceiver(), address.getPhone(), address.isDefault());
    }

    public Address toEntity(Users user) {
        return Address.createAddress(user, naming, postCode, detailAddress, receiver, phone, isDefault);
    }

}
