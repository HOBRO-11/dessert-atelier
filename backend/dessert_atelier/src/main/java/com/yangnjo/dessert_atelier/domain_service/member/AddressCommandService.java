package com.yangnjo.dessert_atelier.domain_service.member;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressUpdateDto;

public interface AddressCommandService {

    Integer create(Long memberId, List<AddressCreateDto> dtos);

    void update(AddressUpdateDto dtos);

    void setDefault(Long addressId, Long memberId, boolean isDefault);

    void delete(Long addressId, Long memberId);
}