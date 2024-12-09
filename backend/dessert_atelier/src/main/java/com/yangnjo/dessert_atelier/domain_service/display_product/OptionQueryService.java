package com.yangnjo.dessert_atelier.domain_service.display_product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.dto.OptionDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;

public interface OptionQueryService {

  Page<OptionSimpleDto> getAllOptionsByDppIdAndStatus(Long dppId, OptionStatus status, PageOption pageOption);

  List<OptionSimpleDto> getAllOptionsByDppIdAndStatus(Long dppId, OptionStatus status);

  OptionDetailDto getOptionDetail(Long optionId);

  List<OptionSimpleDto> getOptionSimpleDtosByOptionIds(List<Long> optionIds);

}