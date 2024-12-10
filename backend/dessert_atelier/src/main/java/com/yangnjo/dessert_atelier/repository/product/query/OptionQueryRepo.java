package com.yangnjo.dessert_atelier.repository.product.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;

public interface OptionQueryRepo {

    List<OptionSimpleDto> findAllSimpleByDpIdAndStatus(Long dpId, OptionStatus status,
            PageOption pageOption);

    Long countByDpIdAndStatus(Long dpId, OptionStatus status);

    Optional<OptionDto> findByOptionId(Long optionId);

    List<OptionSimpleDto> findAllSimpleByOptionIdIn(List<Long> optionIds);

}