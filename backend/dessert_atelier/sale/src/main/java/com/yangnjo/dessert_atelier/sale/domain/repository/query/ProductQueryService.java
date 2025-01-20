package com.yangnjo.dessert_atelier.sale.domain.repository.query;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.sale.dto.ProductDto;

public interface ProductQueryService {

    Page<ProductDto> findAll(PageOption pageOption);

    Optional<ProductDto> findByProductId(Long productId);

}