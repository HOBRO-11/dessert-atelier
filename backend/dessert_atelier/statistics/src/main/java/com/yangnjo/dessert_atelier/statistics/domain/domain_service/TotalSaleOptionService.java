package com.yangnjo.dessert_atelier.statistics.domain.domain_service;

import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleOptionDto;

public interface TotalSaleOptionService {

  Integer upsertTotalSaleOption(TotalSaleOptionDto dto);

}