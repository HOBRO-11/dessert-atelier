package com.yangnjo.dessert_atelier.domain_service.dto;

import com.yangnjo.dessert_atelier.domain.value_type.Destination;

public class DestinationDto {
    String postCode;
    String detailAddress;
    String receiver;
    String phone;

    public DestinationDto(String postCode, String detailAddress, String receiver, String phone) {
        this.postCode = postCode;
        this.detailAddress = detailAddress;
        this.receiver = receiver;
        this.phone = phone;
    }
    
    public Destination toDestination() {
        return new Destination(postCode, detailAddress, receiver, phone);
    }

}
