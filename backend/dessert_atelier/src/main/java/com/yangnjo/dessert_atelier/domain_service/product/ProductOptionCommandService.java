package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductOptionCreateDto;

public interface ProductOptionCommandService {

    Long create(ProductOptionCreateDto dto);

    void updateOptionStatus(Long optionId, OptionStatus status);

}