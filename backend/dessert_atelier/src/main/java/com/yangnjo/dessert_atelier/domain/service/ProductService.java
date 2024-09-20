package com.yangnjo.dessert_atelier.domain.service;

import java.util.List;

import com.yangnjo.dessert_atelier.common.dto.product.ProductDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductSaveDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductUpdateDto;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

public interface ProductService {

    List<ProductDto> getProductsWithStatus(int page, int size, ProductStatus status);

    ProductDto saveProduct(ProductSaveDto dto);

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, ProductUpdateDto dto);

    void deleteProduct(Long id);

}