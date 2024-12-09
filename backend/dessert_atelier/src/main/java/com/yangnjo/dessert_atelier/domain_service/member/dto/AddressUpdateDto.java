package com.yangnjo.dessert_atelier.domain_service.member.dto;

import com.yangnjo.dessert_atelier.domain_service.dto.DestinationDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressUpdateDto {
    Long addressId;
    Long memberId;
    String naming;
    DestinationDto destinationDto;
}
