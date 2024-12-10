package com.yangnjo.dessert_atelier.repository.react.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_model.react.QReview;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewOrigin;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ReviewDto {
    private Long id;
    private Long displayProductId;
    private Long memberId;
    @Setter
    private String name;
    private List<ImageSrc> images;
    private ReviewStatus status;
    private Integer rate;
    private String comment;
    private ReviewOrigin origin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    

    public ReviewDto(Long id, Long displayProductId, Long memberId, List<ImageSrc> images, ReviewStatus status,
            Integer rate, String comment, ReviewOrigin origin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.displayProductId = displayProductId;
        this.memberId = memberId;
        this.images = images;
        this.status = status;
        this.rate = rate;
        this.comment = comment;
        this.origin = origin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public static Expression<ReviewDto> asDto() {
        QReview review = QReview.review;
        return Projections.constructor(ReviewDto.class,
                review.id,
                review.displayProduct.id,
                review.member.id,
                review.images,
                review.reviewStatus,
                review.rate,
                review.comment,
                review.origin,
                review.createdAt,
                review.updatedAt);
    }

}
