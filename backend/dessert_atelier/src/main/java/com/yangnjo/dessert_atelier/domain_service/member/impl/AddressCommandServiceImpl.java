package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain_model.member.Address;
import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;
import com.yangnjo.dessert_atelier.domain_service.member.AddressCommandService;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressCreateDto;
import com.yangnjo.dessert_atelier.domain_service.member.dto.AddressUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.member.exception.AddressNonAuthException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.AddressNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.repository.member.AddressRepository;
import com.yangnjo.dessert_atelier.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class AddressCommandServiceImpl implements AddressCommandService {

    private static final int ADDRESS_MAX_COUNT = 5;
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    @Override
    public Integer create(final Long memberId, final List<AddressCreateDto> dto) {
        Member member = findMemberById(memberId);

        List<Address> addresses = member.getAddresses();
        if (addresses.size() + dto.size() > ADDRESS_MAX_COUNT) {
            throw new IllegalArgumentException("주소록에 저장할 수 있는 최대 개수를 넘겼습니다.");
        }

        long defaultCnt = dto.stream().filter(acd -> acd.isDefault()).count();
        if (defaultCnt > 1) {
            throw new IllegalArgumentException("기본 주소는 하나만 설정할 수 있습니바.");
        } else if (defaultCnt == 1) {
            addresses.forEach(address -> address.setDefault(false));
        }

        List<Address> saveAddresses = dto.stream().map(acd -> acd.toEntity(member)).collect(Collectors.toList());
        return addressRepository.saveAll(saveAddresses).size();
    }

    @Override
    public void update(final AddressUpdateDto dto) {
        Long addressId = dto.getAddressId();
        Long memberId = dto.getMemberId();
        String naming = dto.getNaming();
        Destination destination = dto.getDestination();

        Address address = findAddressById(addressId);

        checkIsMine(memberId, address);

        if (StringUtils.hasText(naming)) {
            address.setNaming(naming);
        }
        if (destination != null) {
            address.setDestination(destination);
        }
    }

    @Override
    public void setDefault(Long addressId, Long memberId, boolean isDefault) {
        Member member = findMemberById(memberId);
        Address address = findAddressById(addressId);

        checkIsMine(memberId, address);

        List<Address> addresses = member.getAddresses();
        for (Address a : addresses) {
            a.setDefault(false);
        }
        address.setDefault(isDefault);
    }

    @Override
    public void delete(Long addressId, Long memberId) {
        Address address = findAddressById(addressId);

        checkIsMine(memberId, address);

        addressRepository.deleteById(addressId);
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    private Address findAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException());
        return address;
    }

    private void checkIsMine(Long memberId, Address address) {
        if (address.getMember().getId() != memberId) {
            throw new AddressNonAuthException();
        }
    }
}
