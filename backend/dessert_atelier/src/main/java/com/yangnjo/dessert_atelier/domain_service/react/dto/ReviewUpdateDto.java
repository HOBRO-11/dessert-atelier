package com.yangnjo.dessert_atelier.domain_service.react.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewUpdateDto {
    Long reviewId;
    Long memberId;
    String comment;
}
