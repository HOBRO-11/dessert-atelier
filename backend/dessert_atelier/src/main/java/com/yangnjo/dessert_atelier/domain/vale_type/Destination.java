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

    public static Destination createDestination(String postCode, String detailAddress, String receiver, Integer phone) {
        Destination destination = new Destination();
        destination.postCode = postCode;
        destination.detailAddress = detailAddress;
        destination.receiver = receiver;
        destination.phone = phone;
        return destination;
    }

}
