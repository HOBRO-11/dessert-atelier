package com.yangnjo.dessert_atelier.repository.product.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionSimpleDto;

public interface ProductOptionQueryRepo {

    boolean existsByOptionHeaderIdInAndStatus(List<Long> optionHeaderIds, OptionStatus status);

    List<ProductOptionSimpleDto> findAllSimpleByOptionHeaderIdAndStatus(Long optionHeaderId, OptionStatus status,
            PageOption pageOption);

    Long countByOptionHeaderIdAndStatus(Long optionHeaderId, OptionStatus status);

    Optional<ProductOptionDto> findByOptionId(Long optionId);

    List<ProductOptionSimpleDto> findAllSimpleByOptionIdIn(List<Long> optionIds);

}