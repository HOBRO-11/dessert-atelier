package com.yangnjo.dessert_atelier.domain_service.member.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_service.member.MemberCommandService;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.MemberUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Override
    public Long createMember(final MemberCreateDto dto) {
        checkExistEmail(dto.getEmail());
        Member member = dto.toEntity();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Override
    public void updateMemberInfo(final MemberUpdateDto dto) {
        Long memberId = dto.getMemberId();
        String name = dto.getName();
        String phone = dto.getPhone();

        Member member = findMemberById(memberId);
        
        if (StringUtils.hasText(name)) {
            member.setName(name);
        }
        if (StringUtils.hasText(phone)) {
            member.setPhone(phone);
        }
    }

    @Override
    public void updateMemberPassword(Long memberId, String password) {
        Member member = findMemberById(memberId);
        if (StringUtils.hasText(password)) {
            member.setPassword(password);
        }
    }

    @Override
    public void banMember(Long memberId) {
        Member member = findMemberById(memberId);
        member.setMemberStatus(MemberStatus.BAN);
    }

    private void checkExistEmail(String email) {
        boolean isExist = memberRepository.existsByEmail(email);
        if (isExist) {
            throw new MemberAlreadyExistException();
        }
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }
}
