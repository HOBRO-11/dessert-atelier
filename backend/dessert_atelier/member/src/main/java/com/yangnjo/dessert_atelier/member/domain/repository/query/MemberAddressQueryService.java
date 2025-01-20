package com.yangnjo.dessert_atelier.member.domain.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.member.dto.MemberAddressDto;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressSimpleDto;


public interface MemberAddressQueryService {

    List<MemberAddressDto> findAllByMemberId(Long memberId);

    List<MemberAddressSimpleDto> findAllSimpleByMemberId(Long memberId);

    Optional<MemberAddressDto> findDefaultAddressByMemberId(Long memberId);
}