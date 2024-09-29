package com.yangnjo.dessert_atelier.common.dto.review;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.db.model.ReviewOrigin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    private Long displayProductId;

    private Long userId;

    private List<String> imagesUrl;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ReviewOrigin origin;

}
