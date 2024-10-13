package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;
import static com.yangnjo.dessert_atelier.domain.react.QQnA.*;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;

import lombok.Getter;

@Getter
public class QnADto {

    private Long id;
    private String memberName;
    private String comment;
    private String answer;
    private QnAStatus qnaStatus;
    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;
    private LocalDateTime answerCreatedAt;
    private LocalDateTime answerUpdatedAt;

    public QnADto(Long id, String memberName, String comment, String answer, QnAStatus qnaStatus,
            LocalDateTime commentCreatedAt, LocalDateTime commentUpdatedAt, LocalDateTime answerCreatedAt,
            LocalDateTime answerUpdatedAt) {
        this.id = id;
        if (memberName == null) {
            this.memberName = "익명";
        } else {
            this.memberName = memberName;
        }
        this.comment = comment;
        this.answer = answer;
        this.qnaStatus = qnaStatus;
        this.commentCreatedAt = commentCreatedAt;
        this.commentUpdatedAt = commentUpdatedAt;
        this.answerCreatedAt = answerCreatedAt;
        this.answerUpdatedAt = answerUpdatedAt;
    }

    public static Expression<QnADto> asDto() {
        return Projections.constructor(QnADto.class,
                qnA.id,
                member.name,
                qnA.comment,
                qnA.answer,
                qnA.qnaStatus,
                qnA.commentCreatedAt,
                qnA.commentUpdatedAt,
                qnA.answerCreatedAt,
                qnA.answerUpdatedAt);
    }
}
