package com.yangnjo.dessert_atelier.repository.member.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;

public interface MemberQueryRepo {

    Optional<MemberSimpleDto> findSimpleById(Long id);

    Optional<MemberDto> findById(Long id);

    Optional<MemberDto> findByEmail(String email);

    List<MemberSimpleDto> findSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption);

    Long countSimplesByMemberStatus(MemberStatus memberStatus);

}