package com.yangnjo.dessert_atelier.domain_service.react;

import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewCreateDto;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewUpdateDto;

public interface ReviewCommandService {

    Long createMemberReview(ReviewCreateDto dto);

    Long createStoreReview(ReviewCreateDto dto);

    void updateReview(ReviewUpdateDto dto);

    void updateReviewStatus(Long reviewId, ReviewStatus status);

    void deleteReview(Long reviewId, Long memberId);

}