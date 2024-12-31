package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
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
    public Optional<MemberDto> getByEmail(String email) {
        return memberQueryRepo.findByEmail(email);
    }

    @Override
    public Page<MemberSimpleDto> getSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption) {
        List<MemberSimpleDto> dtos = memberQueryRepo.findSimplesByMemberStatus(memberStatus, pageOption);
        return PageResponse.of(dtos, pageOption, () -> memberQueryRepo.countSimplesByMemberStatus(memberStatus));
    }

    @Override
    public Optional<MemberDto> getById(Long id) {
        return memberQueryRepo.findById(id);
    }

    @Override
    public Optional<MemberSimpleDto> getSimpleById(Long id) {
        return memberQueryRepo.findSimpleById(id);
    }

}
