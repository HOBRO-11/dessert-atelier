package com.yangnjo.dessert_atelier.domain_service.react.dto;

import java.util.List;
import java.util.Map;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.react.Review;
import com.yangnjo.dessert_atelier.domain.react.ReviewImage;
import com.yangnjo.dessert_atelier.domain.react.ReviewOrigin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCreateDto {
  Long dpId;
  Long memberId;
  Map<String, String> imageUrls;
  List<Long> optionIds;
  Integer rate;
  String comment;
  ReviewOrigin origin;

  public Review toMemberReviewEntity(DisplayProduct dp, Member member, ReviewImage reviewImage, List<Option> options) {
    return Review.createUserReviews(dp, member, reviewImage, options, rate, comment);
  }

  public Review toStoreReviewEntity(DisplayProduct dp, ReviewImage reviewImage, List<Option> options) {
    return Review.createStoreReviews(dp, reviewImage, options, rate, comment, origin);
  }
}
