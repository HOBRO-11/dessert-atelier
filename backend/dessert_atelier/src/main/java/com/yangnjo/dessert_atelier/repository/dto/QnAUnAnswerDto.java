package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QnAUnAnswerDto {
    
    private Long id;

    private Long displayProductId;

    private Long userId;

    private String comment;

    private LocalDateTime updatedAt;
}
