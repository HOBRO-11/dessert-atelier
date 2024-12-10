package com.yangnjo.dessert_atelier.domain_service.react;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.ReviewDto;

public interface ReviewQueryService {

    Page<ReviewDto> getAllByMemberId(Long memberId, PageOption pageOption);

    Page<ReviewDto> getAllByDpIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption);

    Page<ReviewDto> getAllByDpIdAndExceptStatus(Long dpId, ReviewStatus status, PageOption pageOption);

}