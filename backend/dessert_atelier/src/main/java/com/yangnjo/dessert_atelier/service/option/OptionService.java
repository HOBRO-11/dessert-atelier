package com.yangnjo.dessert_atelier.service.option;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.PQCreateResult;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;

public interface OptionService {

    List<OptionSimpleDto> getAllSimpleByDpIdAndStatus(Long dpId, OptionStatus status,
            PageOption pageOption);

    Optional<OptionDto> getByOptionId(Long optionId);

    List<OptionSimpleDto> getAllSimpleByOptionIdIn(List<Long> optionIds);

    PQCreateResult create(List<OptionCreateForm> forms);

    void updateOptionStatus(Long optionId, OptionStatus status);
}
