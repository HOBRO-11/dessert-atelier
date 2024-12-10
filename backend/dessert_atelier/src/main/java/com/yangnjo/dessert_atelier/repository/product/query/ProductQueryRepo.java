package com.yangnjo.dessert_atelier.repository.product.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;

public interface ProductQueryRepo {

    List<ProductDto> find(PageOption pageOption);

    Optional<ProductDto> findByProductId(Long productId);

}