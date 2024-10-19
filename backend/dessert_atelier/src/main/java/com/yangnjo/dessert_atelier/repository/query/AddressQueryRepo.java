package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.AddressDto;

public interface AddressQueryRepo {

  List<AddressDto> findAllByMemberIdAndIsDefault(Long memberId, Boolean isDefault);

}