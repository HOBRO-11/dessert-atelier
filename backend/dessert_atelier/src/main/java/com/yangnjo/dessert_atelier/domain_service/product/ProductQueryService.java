package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;

public interface ProductQueryService {

    List<ProductDto> get(PageOption pageOption);

    Optional<ProductDto> getByProductId(Long productId);
}
