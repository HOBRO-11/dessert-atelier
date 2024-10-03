package com.yangnjo.dessert_atelier.repository.dto;

import com.yangnjo.dessert_atelier.domain.ReviewOrigin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDisplayDto {

    private Long id;

    private String userName;

    private String comment;

    private String imageUrl;

    private String createdAt;

    private String updatedAt;

    private ReviewOrigin origin;
}
