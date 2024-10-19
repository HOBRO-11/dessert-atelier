package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.dto.ReviewDto;

public interface ReviewQueryRepo {

  List<ReviewDto> findAllByDppIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption);

  Long countAllByDpIdAndStatus(Long dpId, ReviewStatus status);

}