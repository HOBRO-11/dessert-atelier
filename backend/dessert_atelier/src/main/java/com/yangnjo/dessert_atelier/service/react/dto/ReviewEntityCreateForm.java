package com.yangnjo.dessert_atelier.service.react.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.react.ReviewOrigin;
import com.yangnjo.dessert_atelier.domain_service.react.dto.ReviewCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewEntityCreateForm {

    Long dpId;
    Long memberId;
    Integer rate;
    String comment;
    List<String> imageNames;
    ReviewOrigin origin;

    public ReviewCreateDto toDto() {
        if (imageNames == null) {
            return new ReviewCreateDto(dpId, memberId, rate, comment, null, origin);
        }
        
        return new ReviewCreateDto(dpId, memberId, rate, comment, imageNames, origin);
    }
}
