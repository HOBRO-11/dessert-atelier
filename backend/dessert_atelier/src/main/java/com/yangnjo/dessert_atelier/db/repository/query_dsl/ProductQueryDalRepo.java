package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.common.dto.product.ProductDto;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

public interface ProductQueryDalRepo {

    List<ProductDto> findProductsWithStatus(int page, int size, ProductStatus status);

}