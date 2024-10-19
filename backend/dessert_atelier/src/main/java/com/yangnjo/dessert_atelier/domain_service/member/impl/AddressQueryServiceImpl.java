package com.yangnjo.dessert_atelier.domain_service.member.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.member.AddressQueryService;
import com.yangnjo.dessert_atelier.repository.dto.AddressDto;
import com.yangnjo.dessert_atelier.repository.query.AddressQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressQueryServiceImpl implements AddressQueryService {

    private final AddressQueryRepo addressQueryRepo;

    @Override
    public List<AddressDto> getAddressesByMemberIdAndIsDefault(Long memberId, Boolean isDefault) {
        return addressQueryRepo.findAllByMemberIdAndIsDefault(memberId, isDefault);
    }
}
