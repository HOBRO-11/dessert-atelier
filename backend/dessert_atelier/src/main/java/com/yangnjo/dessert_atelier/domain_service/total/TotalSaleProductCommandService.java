package com.yangnjo.dessert_atelier.domain_service.total;

import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductDto;

public interface TotalSaleProductCommandService {

  Integer upsertTotalSaleProduct(TotalSaleProductDto dto);

}