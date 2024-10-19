package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.repository.dto.ProductDto;

public interface ProductQueryRepo {

  List<ProductDto> findByStatus(ProductStatus status, PageOption pageOption);

}