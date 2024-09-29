package com.yangnjo.dessert_atelier.common.dto.qna;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QnAUnAnswerDto {

    public Long id;

    public Long userId;

    public Long dpId;

    public String comment ;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

}
