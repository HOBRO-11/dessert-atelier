package com.yangnjo.dessert_atelier.service.product;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.service.product.dto.ProductCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.ProductUpdateForm;

public interface ProductService {

    Long create(ProductCreateForm form);

    void update(ProductUpdateForm form);

    void setQuantity(Long id, int quantity);

    List<ProductDto> get(PageOption pageOption);

    Optional<ProductDto> getByProductId(Long productId);
}
