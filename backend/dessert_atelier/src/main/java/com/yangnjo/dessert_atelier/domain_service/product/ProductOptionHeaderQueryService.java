package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionHeaderDto;

public interface ProductOptionHeaderQueryService {

    Optional<ProductOptionHeaderDto> getById(Long optionHeaderId);

    List<ProductOptionHeaderDto> getAllByOptionHeaderIdIn(List<Long> optionHeaderIds);

    List<ProductOptionHeaderDto> findAllByDpId(Long displayProductId, PageOption pageOption);

}