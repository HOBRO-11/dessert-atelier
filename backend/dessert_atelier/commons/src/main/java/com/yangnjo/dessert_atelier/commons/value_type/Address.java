package com.yangnjo.dessert_atelier.commons.value_type;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    private String postCode;

    private String detailAddress;

    private String receiver;

    private String phone;

    public Address(String postCode, String detailAddress, String receiver, String phone) {
        this.postCode = postCode;
        this.detailAddress = detailAddress;
        this.receiver = receiver;
        this.phone = phone;
    }

}
