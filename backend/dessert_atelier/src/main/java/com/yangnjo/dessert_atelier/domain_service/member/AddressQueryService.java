package com.yangnjo.dessert_atelier.domain_service.member;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.member.dto.AddressDto;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressSimpleDto;

public interface AddressQueryService {

    List<AddressDto> getAllByMemberId(Long memberId);

    List<AddressSimpleDto> getAllSimpleByMemberId(Long memberId);

    Optional<AddressDto> getDefaultAddressByMemberId(Long memberId);

}