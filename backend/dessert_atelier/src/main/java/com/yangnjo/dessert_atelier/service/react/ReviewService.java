package com.yangnjo.dessert_atelier.service.react;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.ReviewDto;
import com.yangnjo.dessert_atelier.service.react.dto.ReviewEntityCreateForm;
import com.yangnjo.dessert_atelier.service.react.dto.ReviewUpdateForm;

public interface ReviewService {

    Page<ReviewDto> getAllByMemberId(Long memberId, PageOption pageOption);

    Page<ReviewDto> getAllByDpIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption);

    Page<ReviewDto> getAllByDpIdAndExceptStatus(Long dpId, ReviewStatus status, PageOption pageOption);

    Long createMemberReview(ReviewEntityCreateForm form);

    Long createStoreReview(ReviewEntityCreateForm form);

    void updateReview(ReviewUpdateForm form);

    void updateReviewStatus(Long reviewId, ReviewStatus status);

    void deleteReview(Long reviewId);
}
