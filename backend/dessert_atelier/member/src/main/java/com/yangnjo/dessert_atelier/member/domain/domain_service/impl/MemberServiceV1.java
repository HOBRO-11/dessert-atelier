package com.yangnjo.dessert_atelier.member.domain.domain_service.impl;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.member.domain.domain_service.MemberService;
import com.yangnjo.dessert_atelier.member.domain.entity.Member;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberOrigin;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberRole;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;
import com.yangnjo.dessert_atelier.member.domain.repository.MemberRepository;
import com.yangnjo.dessert_atelier.member.dto.MemberCreateDto;
import com.yangnjo.dessert_atelier.member.exception.MemberAlreadyExistException;
import com.yangnjo.dessert_atelier.member.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceV1 implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void updateStatus(final Long memberId, final MemberStatus memberStatus) {
        Member member = findMemberById(memberId);

        if (member.getMemberRole() == MemberRole.OWNER) {
            throw new AccessDeniedException("Authentication");
        }

        member.setStatus(memberStatus);
    }

    @Override
    @Transactional
    public Long createMember(final MemberCreateDto dto) {
        Optional<Member> optionalMember = findMemberByEmail(dto.getEmail());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            MemberRole memberRole = member.getMemberRole();

            if (memberRole == MemberRole.ADMIN || memberRole == MemberRole.OWNER) {
                throw new MemberAlreadyExistException();
            }

            MemberOrigin memberOrigin = member.getMemberOrigin();
            throw new MemberAlreadyExistException(memberOrigin);
        }

        Member member = dto.toEntityForMember();
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }

    @Override
    @Transactional
    public Long createAdmin(final MemberCreateDto dto) {
        checkEmailUnique(dto.getEmail());
        return memberRepository.save(dto.toEntityForAdmin()).getId();
    }

    @Override
    @Transactional
    public Long createOwner(final MemberCreateDto dto) {
        checkEmailUnique(dto.getEmail());

        boolean isOwnerExist = getIsOwnerExist();
        if (isOwnerExist) {
            throw new AccessDeniedException("Authentication");
        }

        return memberRepository.save(dto.toEntityForOwner()).getId();
    }

    private boolean getIsOwnerExist() {
        return memberRepository.existsByMemberRoleAndStatus(MemberRole.OWNER,
                MemberStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void updateName(final Long memberId, final String name) {
        Member member = findMemberById(memberId);
        member.setName(name);
    }

    @Override
    @Transactional
    public void updatePhone(final Long memberId, final String phone) {
        Member member = findMemberById(memberId);
        member.setPhone(phone);
    }

    @Override
    @Transactional
    public void delete(Long memberId) {
        findMemberById(memberId);
        memberRepository.deleteById(memberId);
    }

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Optional<Member> findMemberByEmail(final String email) {
        return memberRepository.findByEmail(email);
    }

    private void checkEmailUnique(String email) {
        boolean isEmailUnique = memberRepository.existsByEmail(email);

        if (isEmailUnique) {
            throw new MemberAlreadyExistException();
        }
    }

}