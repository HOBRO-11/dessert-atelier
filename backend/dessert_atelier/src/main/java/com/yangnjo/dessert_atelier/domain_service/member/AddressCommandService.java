package com.yangnjo.dessert_atelier.domain_service.member;

import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressUpdateDto;

public interface AddressCommandService {

  Long createAddress(AddressCreateDto dto);

  void updateAddress(AddressUpdateDto dto);

  void setDefaultAddress(Long addressId, Long memberId, boolean isDefault);

  void deleteAddress(Long addressId, Long memberId);

  void checkAddressCountLtMax(Long memberId);

}