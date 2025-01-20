package com.yangnjo.dessert_atelier.react.domain.entity;

import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
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
public class ProductReview extends BaseEntity {

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_react_id", nullable = false, updatable = false)
    private ProductReact productReact;

    @Type(JsonType.class)
    private List<String> images;

    private Integer rate;

    @Setter
    @Column(nullable = false)
    private String comment;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductReviewStatus status;

    @Builder
    public ProductReview(Long memberId, ProductReact productReact, List<String> images,
            Integer rate, String comment) {
        this.memberId = memberId;
        this.productReact = productReact;
        this.productReact.addProductReview(this);
        this.images = images;
        this.rate = rate;
        this.comment = comment;
        this.status = ProductReviewStatus.PUB;
    }

}
