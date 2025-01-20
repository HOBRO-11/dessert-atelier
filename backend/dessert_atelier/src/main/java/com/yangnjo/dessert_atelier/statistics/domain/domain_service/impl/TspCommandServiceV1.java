package com.yangnjo.dessert_atelier.statistics.domain.domain_service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.statistics.domain.domain_service.TotalSaleProductService;
import com.yangnjo.dessert_atelier.statistics.domain.respository.TotalSaleProductRepository;
import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TspCommandServiceV1 implements TotalSaleProductService {

    private final TotalSaleProductRepository totalSaleProductRepository;

    @Override
    public Integer upsertTotalSaleProduct(final TotalSaleProductDto dto) {
        return totalSaleProductRepository.upsert(dto.getCreatedAt(), dto.getProductId(), dto.getSaleAmount());
    }
}
