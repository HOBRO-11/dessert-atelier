package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionCreateDto;

public interface SaleOptionService {

    Long create(SaleOptionCreateDto dto);

    void updateStatus(Long optionId, SaleOptionStatus status);

}