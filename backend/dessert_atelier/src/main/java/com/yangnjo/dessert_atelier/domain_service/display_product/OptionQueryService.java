package com.yangnjo.dessert_atelier.domain_service.display_product;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.dto.OptionDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;

public interface OptionQueryService {

  Page<OptionSimpleDto> getAllOptionsByDppIdAndStatus(Long dppId, OptionStatus status, PageOption pageOption);

  OptionDetailDto getOptionDetail(Long optionId);

}