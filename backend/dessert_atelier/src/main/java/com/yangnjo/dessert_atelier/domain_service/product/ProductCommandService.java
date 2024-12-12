package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;

public interface ProductCommandService {

    Long create(ProductCreateDto dto);

    void update(ProductUpdateDto dto);

    void setQuantity(Long id, int quantity);

}