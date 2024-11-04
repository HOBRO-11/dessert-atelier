package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSimpleDto {
  
  private Long id;
  private String email;
  private String name;
  private MemberRole memberRole;
  private MemberStatus memberStatus;

  public static Expression<MemberSimpleDto> asDto() {
    return Projections.constructor(MemberSimpleDto.class,
        member.id,
        member.email,
        member.name,
        member.memberRole,
        member.memberStatus);
  }


}
