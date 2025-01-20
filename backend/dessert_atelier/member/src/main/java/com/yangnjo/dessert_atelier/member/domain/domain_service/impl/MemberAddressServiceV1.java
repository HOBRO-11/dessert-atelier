package com.yangnjo.dessert_atelier.member.domain.domain_service.impl;


import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.commons.aop.AccessCheckable;
import com.yangnjo.dessert_atelier.commons.aop.AccessChecker;
import com.yangnjo.dessert_atelier.commons.value_type.Address;
import com.yangnjo.dessert_atelier.member.domain.domain_service.MemberAddressService;
import com.yangnjo.dessert_atelier.member.domain.entity.Member;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberAddress;
import com.yangnjo.dessert_atelier.member.domain.repository.MemberAddressRepository;
import com.yangnjo.dessert_atelier.member.domain.repository.MemberRepository;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressCreateDto;
import com.yangnjo.dessert_atelier.member.exception.MemberAddressMaxCountException;
import com.yangnjo.dessert_atelier.member.exception.MemberAddressNotFoundException;
import com.yangnjo.dessert_atelier.member.exception.MemberAddressUniqueNameException;
import com.yangnjo.dessert_atelier.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.member.properties.MemberAddressProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberAddressServiceV1 implements MemberAddressService, AccessCheckable {

    private final MemberAddressProperties memberAddressProp;
    private final MemberAddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Override
    public void check(Long memberId, Long entityId) throws AccessDeniedException {
        MemberAddress address = findAddressById(entityId);
        if (address.getMember().getId() != memberId) {
            throw new AccessDeniedException("Authentication");
        }
    }

    @Override
    public void create(final Long memberId, final MemberAddressCreateDto dto) {
        List<MemberAddress> memberAddresses = null;
        Member member = findMemberById(memberId);
        memberAddresses = member.getMemberAddresses();
        boolean isAddressEmpty = memberAddresses == null || memberAddresses.isEmpty();

        // member의 주소록이 텅 빈 경우
        if (isAddressEmpty) {
            MemberAddress entity = dto.toEntity(member);
            entity.setDefault(true);
            addressRepository.save(entity);
            return;
        }

        if (memberAddresses.size() >= memberAddressProp.getMaxCount()) {
            throw new MemberAddressMaxCountException();
        }

        checkUniqueNaming(dto.getNaming(), memberAddresses, null);

        if (dto.isDefault()) {
            memberAddresses.forEach(ma -> ma.setDefault(false));
        }

        addressRepository.save(dto.toEntity(member));
    }

    private void checkUniqueNaming(String naming, List<MemberAddress> memberAddresses, Long exceptionMemberAddressId) {
        boolean isUniqueNaming = memberAddresses.stream().filter(ma -> {
            if (exceptionMemberAddressId != null && ma.getId() == exceptionMemberAddressId) {
                return false;
            }
            return ma.getNaming().equals(naming);
        }).findFirst().isEmpty();
        if (isUniqueNaming == false) {
            throw new MemberAddressUniqueNameException(naming);
        }
    }

    @Override
    @AccessChecker(entityId = "addressId")
    public void updateAddress(Long addressId, Long memberId, Address address) {
        MemberAddress memberAddress = findAddressById(addressId);
        memberAddress.setAddress(address);
    }

    @Override
    @AccessChecker(entityId = "addressId")
    public void updateNaming(Long addressId, Long memberId, String naming) {
        Member member = findMemberById(memberId);
        checkUniqueNaming(naming, member.getMemberAddresses(), addressId);
        MemberAddress memberAddress = findAddressById(addressId);
        memberAddress.setNaming(naming);
    }

    @Override
    public void setDefault(Long addressId, Long memberId) {
        Member member = findMemberById(memberId);
        List<MemberAddress> addresses = member.getMemberAddresses();

        boolean isFound = false;
        for (MemberAddress address : addresses) {
            if (address.getId().equals(addressId)) {
                address.setDefault(true);
                isFound = true;
                continue;
            }
            address.setDefault(false);
        }

        if (isFound == false) {
            throw new MemberAddressNotFoundException();
        }
    }

    @Override
    @AccessChecker(entityId = "addressId")
    public void delete(Long addressId, Long memberId) {
        findAddressById(addressId);
        addressRepository.deleteById(addressId);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
    }

    private MemberAddress findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new MemberAddressNotFoundException());
    }

}
