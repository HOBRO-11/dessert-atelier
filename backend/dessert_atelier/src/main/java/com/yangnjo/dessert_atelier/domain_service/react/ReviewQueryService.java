package com.yangnjo.dessert_atelier.domain_service.react;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.dto.ReviewDto;

public interface ReviewQueryService {

  Page<ReviewDto> getReviewsByDpIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption);

}