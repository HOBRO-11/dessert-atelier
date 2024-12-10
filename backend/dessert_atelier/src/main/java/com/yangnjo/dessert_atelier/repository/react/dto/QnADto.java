package com.yangnjo.dessert_atelier.repository.react.dto;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.react.QQnA;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
public class QnADto {

    private Long id;
    private Long displayProductId;
    private Long memberId;
    @Setter
    private String name;
    private String password;
    private String comment;
    private String answer;
    private QnAStatus qnaStatus;
    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;
    private LocalDateTime answerCreatedAt;
    private LocalDateTime answerUpdatedAt;

    

    public QnADto(Long id, Long displayProductId, Long memberId, String password, String comment, String answer,
            QnAStatus qnaStatus, LocalDateTime commentCreatedAt, LocalDateTime commentUpdatedAt,
            LocalDateTime answerCreatedAt, LocalDateTime answerUpdatedAt) {
        this.id = id;
        this.displayProductId = displayProductId;
        this.memberId = memberId;
        this.password = password;
        this.comment = comment;
        this.answer = answer;
        this.qnaStatus = qnaStatus;
        this.commentCreatedAt = commentCreatedAt;
        this.commentUpdatedAt = commentUpdatedAt;
        this.answerCreatedAt = answerCreatedAt;
        this.answerUpdatedAt = answerUpdatedAt;
    }



    public static Expression<QnADto> asDto() {
        QQnA qnA = QQnA.qnA;
        return Projections.constructor(QnADto.class,
                qnA.id,
                qnA.displayProduct.id,
                qnA.member.id,
                qnA.password,
                qnA.comment,
                qnA.answer,
                qnA.qnaStatus,
                qnA.commentCreatedAt,
                qnA.commentUpdatedAt,
                qnA.answerCreatedAt,
                qnA.answerUpdatedAt);
    }
}
