package com.yangnjo.dessert_atelier.domain_service.total.impl;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.domain_service.total.TotalSaleOptionCommandService;
import com.yangnjo.dessert_atelier.repository.total.TotalSaleOptionRepository;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TsoCommandServiceImpl implements TotalSaleOptionCommandService {

  private final TotalSaleOptionRepository totalSaleOptionRepository;

  @Override
  public Integer upsertTotalSaleOption(final TotalSaleOptionDto dto) {
    return totalSaleOptionRepository.upsert(dto.getCreatedAt(), dto.getOptionId(), dto.getSaleAmount());
  }
}
