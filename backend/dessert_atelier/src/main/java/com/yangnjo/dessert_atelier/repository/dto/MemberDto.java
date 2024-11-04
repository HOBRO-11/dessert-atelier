package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
  
  private Long id;
  private String email;
  private String name;
  private String phone;
  private MemberRole memberRole;
  private MemberStatus memberStatus;
  private MemberOrigin memberOrigin;

  public static Expression<MemberDto> asDto() {
    return Projections.constructor(MemberDto.class,
        member.id,
        member.email,
        member.name,
        member.phone,
        member.memberRole,
        member.memberStatus,
        member.memberOrigin);
  }
}