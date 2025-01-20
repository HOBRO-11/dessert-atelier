package com.yangnjo.dessert_atelier.member.dto;

import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.member.domain.entity.Member;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberAddressCreateDto {
    String naming;
    Address address;
    boolean isDefault;

    public MemberAddress toEntity(Member member) {
        return MemberAddress.builder()
                .member(member)
                .naming(naming)
                .address(address)
                .isDefault(isDefault)
                .build();
    }
}
