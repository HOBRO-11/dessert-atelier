package com.yangnjo.dessert_atelier.domain_service.member;

import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberUpdateDto;

public interface MemberCommandService {

  Long createMember(MemberCreateDto dto);

  void updateMemberInfo(MemberUpdateDto dto);

  void updateMemberPassword(Long memberId, String password);

  void banMember(Long memberId);

}