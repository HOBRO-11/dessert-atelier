package com.yangnjo.dessert_atelier.domain_service.member.dto;

import com.yangnjo.dessert_atelier.domain.member.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressCreateDto {
  Long memberId;
  String naming;
  String postCode;
  String detailAddress;
  String receiver;
  String phone;
  boolean isDefault;

  
  public Address toEntity(){
    return new Address(naming, postCode, detailAddress, receiver, phone, isDefault);
  }
}
