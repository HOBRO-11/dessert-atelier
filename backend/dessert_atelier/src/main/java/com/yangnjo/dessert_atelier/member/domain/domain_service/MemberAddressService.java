package com.yangnjo.dessert_atelier.member.domain.domain_service;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressCreateDto;

public interface MemberAddressService {

    void create(Long memberId, MemberAddressCreateDto dto);

    void updateNaming(Long addressId, Long memberId, String naming);

    void updateAddress(Long addressId, Long memberId, Address address);

    void setDefault(Long addressId, Long memberId);

    void delete(Long addressId, Long memberId);

}