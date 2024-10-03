package com.yangnjo.dessert_atelier.domain.vale_type;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Destination {

    private String postCode;

    private String detailAddress;

    private String receiver;

    private Integer phone;

    public Destination(String postCode, String detailAddress, String receiver, Integer phone) {
        this.postCode = postCode;
        this.detailAddress = detailAddress;
        this.receiver = receiver;
        this.phone = phone;
    }

}
