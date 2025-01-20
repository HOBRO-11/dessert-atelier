package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.ProductQuantityCreateDto;

public interface ProductQuantityService {

    Long create(ProductQuantityCreateDto dto);

    void setQuantity(Long productQuantityId, Integer quantity);

    void delete(List<Long> productQuantityIds);
}
