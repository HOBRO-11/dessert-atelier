package com.yangnjo.dessert_atelier.common.dto.qna;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.db.model.QnAStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QnADto {

    public Long id;

    public Long userId;

    private Long dpId;

    public String comment;

    public LocalDateTime commentUpdatedAt;

    public String answer;

    public LocalDateTime answerUpdatedAt;

    public QnAStatus status;


}
