package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductUpdateDto;

public interface DisplayProductCommandService {

    Long create(DisplayProductCreateDto dto);

    void update(DisplayProductUpdateDto dto);

    void updateDisplayProductStatus(Long dpId, DisplayProductStatus saleStatus);
    
}