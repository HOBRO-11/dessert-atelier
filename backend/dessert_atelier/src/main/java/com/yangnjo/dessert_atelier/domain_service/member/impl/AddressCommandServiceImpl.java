package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain.member.Address;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.value_type.Destination;
import com.yangnjo.dessert_atelier.domain_service.member.AddressCommandService;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.member.exception.AddressCountMaxException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.AddressNonAuthException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.AddressNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.repository.AddressRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class AddressCommandServiceImpl implements AddressCommandService {

    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;
    private static final int ADDRESS_MAX_COUNT = 20;

    @Override
    public Long createAddress(final AddressCreateDto dto) {
        Member member = findMemberById(dto.getMemberId());
        
        Address address = dto.toEntity();
        member.addAddress(address);
        Address savedAddress = addressRepository.save(address);
        return savedAddress.getId();
    }

    @Override
    public void updateAddress(final AddressUpdateDto dto) {
        Long addressId = dto.getAddressId();
        Long memberId = dto.getMemberId();
        String naming = dto.getNaming();
        Destination destination = dto.getDestination();

        Address address = findAddressById(addressId);

        checkExistMember(memberId);
        checkAuthMember(memberId, address);

        if (StringUtils.hasText(naming)) {
            address.setNaming(naming);
        }
        if (destination != null) {
            address.setDestination(destination);
        }
    }

    @Override
    public void setDefaultAddress(Long addressId, Long memberId, boolean isDefault) {
        Member member = findMemberById(memberId);
        Address address = findAddressById(addressId);

        checkAuthMember(memberId, address);

        List<Address> addresses = member.getAddresses();
        for (Address a : addresses) {
            a.setDefault(false);
        }
        address.setDefault(isDefault);
    }

    @Override
    public void deleteAddress(Long addressId, Long memberId) {
        Address address = findAddressById(addressId);
        
        checkExistMember(memberId);
        checkAuthMember(memberId, address);

        addressRepository.deleteById(addressId);
    }

    @Override
    public void checkAddressCountLtMax(Long memberId) {
        Member member = findMemberById(memberId);
        int count = member.getAddresses().size();
        if (count >= ADDRESS_MAX_COUNT) {
            throw new AddressCountMaxException();
        }
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    private void checkExistMember(Long memberId) {
        boolean isExist = memberRepository.existsById(memberId);
        if (isExist == false) {
            throw new MemberNotFoundException();
        }
    }

    private Address findAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException());
        return address;
    }

    private void checkAuthMember(Long memberId, Address address) {
        if (address.getMember().getId() != memberId) {
            throw new AddressNonAuthException();
        }
    }
}
