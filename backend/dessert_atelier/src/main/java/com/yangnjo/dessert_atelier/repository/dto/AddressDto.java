package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.member.QAddress.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDto {

  private Long id;
  private String naming;
  private String postCode;
  private String detailAddress;
  private String receiver;
  private String phone;
  private boolean isDefault;

  public static Expression<AddressDto> asDto() {
    return Projections.constructor(AddressDto.class,
        address.id,
        address.naming,
        address.destination.postCode,
        address.destination.detailAddress,
        address.destination.receiver,
        address.destination.phone,
        address.isDefault);
  }
}
