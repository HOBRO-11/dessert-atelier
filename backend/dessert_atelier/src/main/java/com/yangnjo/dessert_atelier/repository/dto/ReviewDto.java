package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;
import static com.yangnjo.dessert_atelier.domain.react.QReview.*;

import java.time.LocalDateTime;
import java.util.Map;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.react.ReviewOrigin;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;

import lombok.Getter;

@Getter
public class ReviewDto {
  private Long id;
  private Long memberId;
  private String memberName;
  private Map<String, String> imageUrlsMap;
  private Integer rate;
  private String comment;
  private ReviewStatus status;
  private ReviewOrigin origin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  public ReviewDto(Long id, Long memberId, String memberName, Map<String, String> imageUrlsMap, Integer rate, String comment,
      ReviewStatus status, ReviewOrigin origin, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.memberId = memberId;
    if (memberName == null) {
      this.memberName = "익명";
    } else {
      this.memberName = memberName;
    }
    this.imageUrlsMap = imageUrlsMap;
    this.rate = rate;
    this.comment = comment;
    this.status = status;
    this.origin = origin;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Expression<ReviewDto> asIncompleteDto() {
    return Projections.constructor(ReviewDto.class,
        review.id,
        review.member.id,
        member.name,
        review.reviewImage.imageUrls,
        review.rate,
        review.comment,
        review.reviewStatus,
        review.origin,
        review.createdAt,
        review.updatedAt);
  }

}
