package com.yangnjo.dessert_atelier.member.domain.domain_service;

import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;
import com.yangnjo.dessert_atelier.member.dto.MemberCreateDto;

public interface MemberService {

    Long createMember(final MemberCreateDto dto);

    Long createAdmin(final MemberCreateDto dto);

    Long createOwner(final MemberCreateDto dto);

    void updatePhone(final Long memberId, final String phone);

    void updateName(final Long memberId, final String name);

    void updateStatus(final Long memberId, final MemberStatus memberStatus);

    void delete(Long memberId);

}