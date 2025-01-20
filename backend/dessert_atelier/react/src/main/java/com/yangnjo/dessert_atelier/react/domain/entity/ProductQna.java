package com.yangnjo.dessert_atelier.react.domain.entity;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(indexes = {@Index(columnList = "member_id")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductQna extends BaseEntity {

    private Long memberId;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_react_id", nullable = false, updatable = false)
    private ProductReact productReact;

    @Column(nullable = false)
    private String question;

    private String answer;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductQnAStatus status;

    @Builder
    public ProductQna(Long memberId, String password, ProductReact productReact, String question, String answer,
            ProductQnAStatus status) {
        this.memberId = memberId;
        this.password = password;
        this.productReact = productReact;
        this.productReact.addProductQnA(this);
        this.question = question;
        this.answer = answer;
        this.status = status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.status = ProductQnAStatus.RESP;
    }

    public void removeAnswer() {
        this.answer = null;
        this.status = ProductQnAStatus.REQ;
    }

}
