package com.yangnjo.dessert_atelier.domain_service.total;

import com.yangnjo.dessert_atelier.repository.dto.TotalSaleOptionDto;

public interface TotalSaleOptionCommandService {

  Integer upsertTotalSaleOption(TotalSaleOptionDto dto);

}