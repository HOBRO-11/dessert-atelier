package com.yangnjo.dessert_atelier.service.member;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;
import com.yangnjo.dessert_atelier.service.member.dto.MemberUpdateForm;

public interface MemberService {
    Optional<MemberSimpleDto> getSimpleById(Long id);

    Optional<MemberDto> getById(Long id);

    Optional<MemberDto> getByEmail(String email);

    Page<MemberSimpleDto> getSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption);

    void updateMemberInfo(MemberUpdateForm dto);

    void activeMember(Long memberId);

    void banMember(Long memberId);

    void inactivateMember(Long memberId);
}
