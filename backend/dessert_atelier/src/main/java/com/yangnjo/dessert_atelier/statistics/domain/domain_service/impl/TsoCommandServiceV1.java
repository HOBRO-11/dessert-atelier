package com.yangnjo.dessert_atelier.statistics.domain.domain_service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.statistics.domain.domain_service.TotalSaleOptionService;
import com.yangnjo.dessert_atelier.statistics.domain.respository.TotalSaleOptionRepository;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleOptionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TsoCommandServiceV1 implements TotalSaleOptionService {

    private final TotalSaleOptionRepository totalSaleOptionRepository;

    @Override
    public Integer upsertTotalSaleOption(final TotalSaleOptionDto dto) {
        return totalSaleOptionRepository.upsert(dto.getCreatedAt(), dto.getOptionId(), dto.getSaleAmount());
    }
}
