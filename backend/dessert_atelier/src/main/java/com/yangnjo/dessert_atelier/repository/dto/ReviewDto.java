package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;
import static com.yangnjo.dessert_atelier.domain.react.QReview.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.react.ReviewOrigin;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ReviewDto {
  private Long id;
  private String memberName;
  private Map<String, String> imageUrlsMap;
  private Integer rate;
  private String comment;
  private ReviewStatus status;
  private ReviewOrigin origin;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  @Setter
  private List<String> optionDescriptions;

  /*
   * 잠시 optionIds를 넣어두는 용도로 사용된다.
   */
  private List<Long> optionIds;

  /*
   * 반드시 setOptionDescriptions 을 사용하여 DTO 완성을 마무리 짓자.
   * @see optionDescriptions
   */
  public ReviewDto(Long id, String memberName, Map<String, String> imageUrlsMap, Integer rate, String comment,
      ReviewStatus status, ReviewOrigin origin, LocalDateTime createdAt, LocalDateTime updatedAt,
      List<Long> optionIds) {
    this.id = id;
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
    this.optionIds = optionIds;
  }

  public static Expression<ReviewDto> asIncompleteDto() {
    return Projections.constructor(ReviewDto.class,
        review.id,
        member.name,
        review.reviewImage.imageUrls,
        review.rate,
        review.comment,
        review.reviewStatus,
        review.origin,
        review.createdAt,
        review.updatedAt,
        review.optionIds);
  }

}
