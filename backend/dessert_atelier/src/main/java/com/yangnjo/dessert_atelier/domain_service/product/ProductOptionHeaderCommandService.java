package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductOptionHeaderCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductOptionHeaderUpdateDto;

public interface ProductOptionHeaderCommandService {

    Long create(Long displayProductId, ProductOptionHeaderCreateDto dto);

    void update(Long optionHeaderId, ProductOptionHeaderUpdateDto dto);

}