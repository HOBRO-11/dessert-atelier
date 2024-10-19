package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.DppDto;
import com.yangnjo.dessert_atelier.repository.dto.DppSimpleDto;

public interface DppQueryRepo {

  Optional<DppDto> find(Long dppId);

  List<DppSimpleDto> findSimpleByDpId(Long dpId, PageOption pageOption, PeriodOption periodOption);

  Long countSimpleByDpId(Long dpId, PeriodOption periodOption);

  List<DppSimpleDto> findSimpleLikeNaming(String naming, PageOption pageOption, PeriodOption periodOption);

  Long countSimpleLikeNaming(String naming, PeriodOption periodOption);

  List<DppSimpleDto> findSimpleByDpIdAndLikeNaming(Long dpId, String naming, PageOption pageOption,
      PeriodOption periodOption);

  Long countSimpleByDpIdAndLikeNaming(Long dpId, String naming, PeriodOption periodOption);

  List<DppSimpleDto> findSimpleByDpIdAndLikeTitle(Long dpId, String title, PageOption pageOption,
      PeriodOption periodOption);

  Long countSimpleByDpIdAndLikeTitle(Long dpId, String title, PeriodOption periodOption);

  List<DppSimpleDto> findByDpIdsAndDefault(List<Long> dpIds, PageOption pageOption);

  Long countByDpIdsAndDefault(List<Long> dpIds);

}