package com.yangnjo.dessert_atelier.domain_service.react.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.react.Review;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewOrigin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewCreateDto {
    Long dpId;
    Long memberId;
    Integer rate;
    String comment;
    List<ImageSrc> images;
    ReviewOrigin origin;

    public Review toMemberReviewEntity(DisplayProduct dp, Member member) {
        return Review.createUserReviews(dp, member, images, rate, comment);
    }

    public Review toStoreReviewEntity(DisplayProduct dp) {
        return Review.createStoreReviews(dp, images, rate, comment, origin); 
    }
}
