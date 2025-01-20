package com.yangnjo.dessert_atelier.react.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.react.domain.entity.ProductReact;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReview;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductReviewCreateDto {
    Long memberId;
    Long productReactId;
    Integer rate;
    String comment;
    List<String> images;

    public ProductReview toMemberReviewEntity(ProductReact productReact) {
        return ProductReview.builder()
                .memberId(memberId)
                .productReact(productReact)
                .images(images)
                .rate(rate)
                .comment(comment)
                .build();
    }

    public ProductReview toStoreReviewEntity(ProductReact productReact) {
        return ProductReview.builder()
                .productReact(productReact)
                .images(images)
                .rate(rate)
                .comment(comment)
                .build();
    }
}
