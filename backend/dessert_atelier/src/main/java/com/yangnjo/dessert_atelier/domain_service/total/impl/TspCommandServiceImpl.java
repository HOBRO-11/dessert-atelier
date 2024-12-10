package com.yangnjo.dessert_atelier.domain_service.total.impl;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.domain_service.total.TotalSaleProductCommandService;
import com.yangnjo.dessert_atelier.repository.total.TotalSaleProductRepository;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TspCommandServiceImpl implements TotalSaleProductCommandService {

  private final TotalSaleProductRepository totalSaleProductRepository;

  @Override
  public Integer upsertTotalSaleProduct(final TotalSaleProductDto dto) {
    return totalSaleProductRepository.upsert(dto.getCreatedAt(), dto.getProductId(), dto.getSaleAmount());
  }
}
