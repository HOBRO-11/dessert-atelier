package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import com.yangnjo.dessert_atelier.sale.dto.ProductCreateDto;

public interface ProductService {

    Long create(ProductCreateDto dto);

    void setStockQuantity(Long productId, int quantity);

    void updatePrice(Long productId, int price);

}