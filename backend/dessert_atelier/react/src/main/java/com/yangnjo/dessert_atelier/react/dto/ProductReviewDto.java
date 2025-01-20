package com.yangnjo.dessert_atelier.react.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReviewStatus;
import com.yangnjo.dessert_atelier.react.domain.entity.QProductReview;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductReviewDto {
    private Long id;
    private Long memberId;
    private Long productReactId;
    private List<String> images;
    private Integer rate;
    private String comment;
    private ProductReviewStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Expression<ProductReviewDto> asDto() {
        QProductReview review = QProductReview.productReview;
        return Projections.constructor(ProductReviewDto.class,
                review.id,
                review.memberId,
                review.productReact.id,
                review.images,
                review.rate,
                review.comment,
                review.status,
                review.createdAt,
                review.updatedAt);
    }

}
