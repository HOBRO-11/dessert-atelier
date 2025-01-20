package com.yangnjo.dessert_atelier.statistics.domain.domain_service;

import com.yangnjo.dessert_atelier.statistics.dto.TotalSaleProductDto;

public interface TotalSaleProductService {

  Integer upsertTotalSaleProduct(TotalSaleProductDto dto);

}