package com.yangnjo.dessert_atelier.repository.react.query;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.ReviewDto;

public interface ReviewQueryRepo {

    List<ReviewDto> findAllByMemberId(Long memberId, PageOption pageOption);

    List<ReviewDto> findAllByDppIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption);

    List<ReviewDto> findAllByDppIdAndExceptStatus(Long dpId, ReviewStatus status, PageOption pageOption);

    Long countAllByMemberId(Long memberId);

    Long countAllByDpIdAndStatus(Long dpId, ReviewStatus status);

    Long countAllByDpIdAndExceptStatus(Long dpId, ReviewStatus status);

}