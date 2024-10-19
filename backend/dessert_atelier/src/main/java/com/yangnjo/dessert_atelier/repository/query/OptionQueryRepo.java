package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.dto.OptionDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;

public interface OptionQueryRepo {

  List<OptionSimpleDto> findAllByDppIdAndStatus(Long dppId, OptionStatus status,
      PageOption pageOption);

  Long countByDppIdAndStatus(Long dppId, OptionStatus status);

  Optional<OptionDetailDto> findDetailByOptionId(Long optionId);

}