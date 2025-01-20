package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.SaleOptionGroupCreateDto;

public interface SaleOptionGroupValidator {
    
    void validateForCreate(List<SaleOptionGroupCreateDto> dto);

    void validateForAdd(SaleOptionGroupCreateDto dto);
}
