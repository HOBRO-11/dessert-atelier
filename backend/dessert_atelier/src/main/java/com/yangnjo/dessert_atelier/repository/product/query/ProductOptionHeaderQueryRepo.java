package com.yangnjo.dessert_atelier.repository.product.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionHeaderDto;

public interface ProductOptionHeaderQueryRepo {

    Optional<ProductOptionHeaderDto> findById(Long optionHeaderId);

    List<ProductOptionHeaderDto> findAllByDpId(Long displayProductId, PageOption pageOption);

    List<ProductOptionHeaderDto> findAllByOptionHeaderIdIn(List<Long> optionHeaderIds);

}