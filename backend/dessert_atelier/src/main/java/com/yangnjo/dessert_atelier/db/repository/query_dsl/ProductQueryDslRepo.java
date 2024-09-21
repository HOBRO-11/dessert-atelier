package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.common.dto.product.ProductDetailDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductDetailDtoExceptImages;
import com.yangnjo.dessert_atelier.common.dto.product.ProductSimpleDto;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

public interface ProductQueryDslRepo {

    List<ProductSimpleDto> findProductsWithStatus(int page, int size, ProductStatus status);

    ProductDetailDtoExceptImages findProductExceptImages(Long id);

    public List<ProductDetailDto> findDetailProduct(Long id);

}