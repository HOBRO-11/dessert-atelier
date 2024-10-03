package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.ProductStatus;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.ProductFlatDto;

public interface ProductQueryDslRepo {

    List<ProductFlatDto> searchWithCondition(PageOption pageOption, Long productId, ProductStatus status);

    Long countWithCondition(Long productId, ProductStatus status);

}