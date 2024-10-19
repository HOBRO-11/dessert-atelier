package com.yangnjo.dessert_atelier.domain_service.member.dto;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateDto {
  String email;
  String password;
  String name;
  String phone;
  MemberOrigin origin;

  public Member toEntity(){
    return new Member(email, password, name, phone, origin);
  }
}
