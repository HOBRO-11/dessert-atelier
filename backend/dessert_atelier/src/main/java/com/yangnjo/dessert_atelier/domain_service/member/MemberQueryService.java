package com.yangnjo.dessert_atelier.domain_service.member;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;

public interface MemberQueryService {

    Optional<MemberSimpleDto> getSimpleById(Long id);

    Optional<MemberDto> getById(Long id);

    Optional<MemberDto> getByEmail(String email);

    Page<MemberSimpleDto> getSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption);

}