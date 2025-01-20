package com.yangnjo.dessert_atelier.react.domain.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReviewStatus;
import com.yangnjo.dessert_atelier.react.dto.ProductReviewDto;

public interface ProductReviewQueryService {

    List<ProductReviewDto> findAllByMemberId(Long memberId, PageOption pageOption);

    List<ProductReviewDto> findAllByProductReactIdAndStatus(Long productReactId, PageOption pageOption, ProductReviewStatus... statuses);

    Long countAllByMemberId(Long memberId);

    Long countAllByProductReactIdAndStatus(Long productReactId, ProductReviewStatus... statuses);

}