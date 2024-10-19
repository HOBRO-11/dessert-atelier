package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.repository.dto.ProductDto;

public interface ProductQueryService {

  List<ProductDto> getProductsByStatus(ProductStatus status, PageOption pageOption);

}