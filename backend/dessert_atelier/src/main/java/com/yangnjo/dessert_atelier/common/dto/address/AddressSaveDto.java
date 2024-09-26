package com.yangnjo.dessert_atelier.common.dto.address;

import com.yangnjo.dessert_atelier.db.entity.Addresses;
import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressSaveDto {

    private Users users;

    private String naming;

    private String postCode;

    private String detailAddress;

    private String receiver;

    private int phone;

    private boolean isDefault;

    public Addresses toEntity() {
        return Addresses.createAddress(users, naming, postCode, detailAddress, receiver, phone, isDefault);
    }
}