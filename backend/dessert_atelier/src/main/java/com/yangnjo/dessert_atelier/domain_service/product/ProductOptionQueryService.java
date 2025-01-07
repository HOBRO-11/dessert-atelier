package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionSimpleDto;

public interface ProductOptionQueryService {

    boolean existsByOptionHeaderIdInAndStatus(List<Long> optionHeaderIds, OptionStatus status);

    List<ProductOptionSimpleDto> getAllSimpleByOptionHeaderAndStatus(Long optionHeaderId, OptionStatus status,
            PageOption pageOption);

    Optional<ProductOptionDto> getByOptionId(Long optionId);

    List<ProductOptionSimpleDto> getAllSimpleByOptionIdIn(List<Long> optionIds);

}