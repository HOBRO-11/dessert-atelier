package com.yangnjo.dessert_atelier.domain_service.member;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.AddressDto;

public interface AddressQueryService {

  List<AddressDto> getAddressesByMemberIdAndIsDefault(Long memberId, Boolean isDefault);

}