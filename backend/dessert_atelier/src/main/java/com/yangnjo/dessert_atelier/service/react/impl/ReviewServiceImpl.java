package com.yangnjo.dessert_atelier.service.react.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.domain_service.react.ReviewCommandService;
import com.yangnjo.dessert_atelier.domain_service.react.ReviewQueryService;
import com.yangnjo.dessert_atelier.repository.react.dto.ReviewDto;
import com.yangnjo.dessert_atelier.service.react.ReviewService;
import com.yangnjo.dessert_atelier.service.react.dto.ReviewEntityCreateForm;
import com.yangnjo.dessert_atelier.service.react.dto.ReviewUpdateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    @Override
    public Page<ReviewDto> getAllByMemberId(Long memberId, PageOption pageOption) {
        return reviewQueryService.getAllByMemberId(memberId, pageOption);
    }

    @Override
    public Page<ReviewDto> getAllByDpIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
        return reviewQueryService.getAllByDpIdAndStatus(dpId, status, pageOption);
    }

    @Override
    public Page<ReviewDto> getAllByDpIdAndExceptStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
        return reviewQueryService.getAllByDpIdAndExceptStatus(dpId, status, pageOption);
    }

    @Override
    public Long createMemberReview(ReviewEntityCreateForm form) {
        return reviewCommandService.createMemberReview(form.toDto());
    }

    @Override
    public Long createStoreReview(ReviewEntityCreateForm form) {
        return reviewCommandService.createStoreReview(form.toDto());
    }

    @Override
    public void updateReview(ReviewUpdateForm form) {
        reviewCommandService.updateReview(form.toDto());
    }

    @Override
    public void updateReviewStatus(Long reviewId, ReviewStatus status) {
        reviewCommandService.updateReviewStatus(reviewId, status);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewCommandService.deleteReview(reviewId);
    }

}
