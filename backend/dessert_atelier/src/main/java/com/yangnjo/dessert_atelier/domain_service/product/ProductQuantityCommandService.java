package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_service.product.dto.PQCreateResult;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductQuantityCreateDto;

public interface ProductQuantityCommandService {
    
    PQCreateResult create(List<ProductQuantityCreateDto> dtos);
}
