package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.SaleOptionGroupCreateDto;

public interface SaleOptionGroupService {
    
    void create(List<SaleOptionGroupCreateDto> dto);

    void addGroup(SaleOptionGroupCreateDto dto);

}
