package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_service.member.MemberQueryService;
import com.yangnjo.dessert_atelier.repository.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.dto.MemberSimpleDto;
import com.yangnjo.dessert_atelier.repository.query.MemberQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberQueryRepo memberQueryRepo;

    @Override
    public MemberDto getMemberByStatusAndOrigin(MemberStatus status, MemberOrigin origin, PageOption pageOption) {
        return memberQueryRepo.findByMemberStatusAndMemberOrigin(status, origin, pageOption);
    }

    @Override
    public Page<MemberSimpleDto> getSimpleMembersByStatusAndOrigin(MemberStatus status, MemberOrigin origin, PageOption pageOption) {
        List<MemberSimpleDto> dtos = memberQueryRepo.findSimplesByMemberStatusAndMemberOrigin(status, origin, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = memberQueryRepo.countSimplesByMemberStatusAndMemberOrigin(status, origin);
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public MemberSimpleDto getMemberById(Long id) {
        return memberQueryRepo.findMemberById(id);
    }

}
