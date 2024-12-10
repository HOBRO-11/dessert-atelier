package com.yangnjo.dessert_atelier.domain_service.member;

import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberUpdateDto;

public interface MemberCommandService {

    Long join(MemberCreateDto dto);

    void updateMemberInfo(MemberUpdateDto dto);

    void updateMemberStatus(Long memberId, MemberStatus memberStatus);

}