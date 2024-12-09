package com.yangnjo.dessert_atelier.domain_service.display_product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.DppDto;
import com.yangnjo.dessert_atelier.repository.dto.DppSimpleDto;

public interface DisplayProductPresetQueryService {

  Optional<DppDto> getDPP(Long dppId);

  Page<DppSimpleDto> getSimpleDPPsByDpId(Long dpId, PageOption pageOption, PeriodOption periodOption);

  Page<DppSimpleDto> getSimpleDPPsLikeNaming(String naming, PageOption pageOption, PeriodOption periodOption);

  List<DppSimpleDto> getSimpleDppsByDppIds(List<Long> dppIds);
}