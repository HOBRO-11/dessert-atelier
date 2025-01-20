package com.yangnjo.dessert_atelier.member.domain.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;
import com.yangnjo.dessert_atelier.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.member.dto.MemberSimpleDto;

public interface MemberQuerService {

    Optional<MemberSimpleDto> findSimpleById(Long id);

    Optional<MemberDto> findById(Long id);

    Optional<MemberDto> findByEmail(String email);

    List<MemberSimpleDto> findSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption);

    Long countSimplesByMemberStatus(MemberStatus memberStatus);

}