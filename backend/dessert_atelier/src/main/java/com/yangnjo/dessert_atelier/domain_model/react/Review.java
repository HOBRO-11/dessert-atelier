package com.yangnjo.dessert_atelier.domain_model.react;

import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.model.BaseEntity;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "display_product_id", nullable = false)
    private DisplayProduct displayProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Type(JsonType.class)
    private List<String> images;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus;

    @Setter
    private Integer rate;

    @Setter
    @Column(nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    private ReviewOrigin origin;

    public static Review createUserReviews(DisplayProduct displayProduct, Member member, List<String> images,
            Integer rate, String comment) {
        Review reviews = new Review();
        reviews.displayProduct = displayProduct;
        reviews.displayProduct.addReview(reviews);
        reviews.member = member;
        member.addReview(reviews);
        reviews.images = images;
        reviews.rate = rate;
        reviews.comment = comment;
        reviews.origin = ReviewOrigin.THIS;
        reviews.reviewStatus = ReviewStatus.PUB;
        return reviews;
    }

    public static Review createStoreReviews(DisplayProduct displayProduct, List<String> images,
            Integer rate, String comment, ReviewOrigin origin) {
        Review reviews = new Review();
        reviews.displayProduct = displayProduct;
        reviews.displayProduct.addReview(reviews);
        reviews.images = images;
        reviews.rate = rate;
        reviews.comment = comment;
        reviews.origin = origin;
        reviews.reviewStatus = ReviewStatus.PUB;
        return reviews;
    }
}
