package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.QnAStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QnAFlatDto {

    private Long id;

    private Long displayProductId;

    private Long userId;

    private String comment;

    private LocalDateTime updatedAt;

    private QnAStatus status;

    private String answer;

    private LocalDateTime answerUpdatedAt;
}
