package com.yangnjo.dessert_atelier.domain_service.member;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;

public interface MemberQueryService {

    MemberSimpleDto getSimpleById(Long id);

    MemberDto getById(Long id);

    MemberDto getByEmail(String email);

    List<MemberSimpleDto> getSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption);

}