package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.member.AddressQueryService;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressDto;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressSimpleDto;
import com.yangnjo.dessert_atelier.repository.member.query.AddressQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressQueryServiceImpl implements AddressQueryService {

    private final AddressQueryRepo addressQueryRepo;

    @Override
    public List<AddressDto> getAllByMemberId(Long memberId) {
        return addressQueryRepo.findAllByMemberId(memberId);
    }

    @Override
    public List<AddressSimpleDto> getAllSimpleByMemberId(Long memberId) {
        return addressQueryRepo.findAllSimpleByMemberId(memberId);
    }

    @Override
    public Optional<AddressDto> getDefaultAddressByMemberId(Long memberId) {
        return addressQueryRepo.findDefaultAddressByMemberId(memberId);
    }

}
