package com.yangnjo.dessert_atelier.react.dto;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductQnAStatus;
import com.yangnjo.dessert_atelier.react.domain.entity.QProductQna;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductQnADto {

    private Long id;
    private Long productReactId;
    private Long memberId;
    private String password;
    private String question;
    private String answer;
    private ProductQnAStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Expression<ProductQnADto> asDto() {
        QProductQna qnA = QProductQna.productQna;
        return Projections.constructor(ProductQnADto.class,
                qnA.id,
                qnA.productReact.id,
                qnA.memberId,
                qnA.password,
                qnA.question,
                qnA.answer,
                qnA.status,
                qnA.createdAt,
                qnA.updatedAt);
    }
}
