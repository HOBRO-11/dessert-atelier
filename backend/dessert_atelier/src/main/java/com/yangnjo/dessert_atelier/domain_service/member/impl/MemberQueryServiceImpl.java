package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_service.member.MemberQueryService;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;
import com.yangnjo.dessert_atelier.repository.member.query.MemberQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberQueryRepo memberQueryRepo;

    @Override
    public MemberDto getByEmail(String email) {
        return memberQueryRepo.findByEmail(email);
    }

    @Override
    public List<MemberSimpleDto> getSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption) {
        return memberQueryRepo.findSimplesByMemberStatus(memberStatus, pageOption);
    }

    @Override
    public MemberDto getById(Long id) {
        return memberQueryRepo.findById(id);
    }

    @Override
    public MemberSimpleDto getSimpleById(Long id) {
        return memberQueryRepo.findSimpleById(id);
    }

}
