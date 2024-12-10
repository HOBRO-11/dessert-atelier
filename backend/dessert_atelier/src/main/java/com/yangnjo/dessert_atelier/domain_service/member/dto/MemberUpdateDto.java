package com.yangnjo.dessert_atelier.domain_service.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDto {
    Long memberId;
    String name;
    String phone;
}
