package com.yangnjo.dessert_atelier.common.dto.review;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewNonReactDto {

    private Long id;

    private Long productId;

    private Long userId;

    private List<String> imagesUrl;

    private String comment;

    private String reviewUpdatedAt;

}
