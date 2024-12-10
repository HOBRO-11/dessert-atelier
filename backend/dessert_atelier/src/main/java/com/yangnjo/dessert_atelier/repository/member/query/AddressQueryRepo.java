package com.yangnjo.dessert_atelier.repository.member.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.member.dto.AddressDto;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressSimpleDto;

public interface AddressQueryRepo {

    List<AddressDto> findAllByMemberId(Long memberId);

    List<AddressSimpleDto> findAllSimpleByMemberId(Long memberId);

    Optional<AddressDto> findDefaultAddressByMemberId(Long memberId);
}