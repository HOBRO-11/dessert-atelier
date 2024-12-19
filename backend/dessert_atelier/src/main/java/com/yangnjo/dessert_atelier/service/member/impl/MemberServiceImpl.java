package com.yangnjo.dessert_atelier.service.member.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_service.member.MemberCommandService;
import com.yangnjo.dessert_atelier.domain_service.member.MemberQueryService;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;
import com.yangnjo.dessert_atelier.service.member.MemberService;
import com.yangnjo.dessert_atelier.service.member.dto.MemberUpdateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @Override
    public Optional<MemberSimpleDto> getSimpleById(Long id) {
        return memberQueryService.getSimpleById(id);
    }

    @Override
    public Optional<MemberDto> getById(Long id) {
        return memberQueryService.getById(id);
    }

    @Override
    public Optional<MemberDto> getByEmail(String email) {
        return memberQueryService.getByEmail(email);
    }

    @Override
    public Page<MemberSimpleDto> getSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption) {
        return memberQueryService.getSimplesByMemberStatus(memberStatus, pageOption);
    }

    @Override
    public void updateMemberInfo(MemberUpdateForm form) {
        memberCommandService.updateMemberInfo(form.toDto());
    }

    @Override
    public void activeMember(Long memberId) {
        memberCommandService.updateMemberStatus(memberId, MemberStatus.ACTIVE);
    }

    @Override
    public void banMember(Long memberId) {
        memberCommandService.updateMemberStatus(memberId, MemberStatus.BAN);
    }

    @Override
    public void inactivateMember(Long memberId) {
        memberCommandService.updateMemberStatus(memberId, MemberStatus.INACTIVE);
    }
    
}
