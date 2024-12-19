package com.yangnjo.dessert_atelier.service.member.dto;

import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberUpdateDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberUpdateForm {
    Long memberId;
    String name;
    String phone;

    public MemberUpdateDto toDto() {
        return new MemberUpdateDto(memberId, name, phone);
    }
}
