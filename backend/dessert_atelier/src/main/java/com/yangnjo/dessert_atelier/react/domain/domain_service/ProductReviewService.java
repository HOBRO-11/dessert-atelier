package com.yangnjo.dessert_atelier.react.domain.domain_service;

import com.yangnjo.dessert_atelier.react.domain.entity.ProductReviewStatus;
import com.yangnjo.dessert_atelier.react.dto.ProductReviewCreateDto;

public interface ProductReviewService {

    Long createByMember(ProductReviewCreateDto dto);

    Long createByStore(ProductReviewCreateDto dto);

    void updateComment(Long memberId, Long reviewId, String comment);
    
    void updateStatus(Long memberId, Long reviewId, ProductReviewStatus status);

    void delete(Long reviewId);

}