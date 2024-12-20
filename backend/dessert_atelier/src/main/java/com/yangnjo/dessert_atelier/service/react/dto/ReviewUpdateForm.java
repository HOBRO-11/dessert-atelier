package com.yangnjo.dessert_atelier.service.react.dto;

import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewUpdateDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewUpdateForm {
    Long reviewId;
    Long memberId;
    String comment;

    public ReviewUpdateDto toDto(){
        return new ReviewUpdateDto(reviewId, memberId, comment);
    }
}
