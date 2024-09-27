package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yangnjo.dessert_atelier.common.dto.product.ProductDetailDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductDetailDtoExceptImages;
import com.yangnjo.dessert_atelier.common.dto.product.ProductSimpleDto;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

public interface ProductQueryDslRepo {

    List<ProductSimpleDto> findSimpleProductsByStatus(int page, int size, ProductStatus status, Direction direction);

    List<ProductDetailDto> findDetailProducts(Long id, Direction direction);

    ProductDetailDtoExceptImages findProductsExceptImages(Long id, Direction direction);

}