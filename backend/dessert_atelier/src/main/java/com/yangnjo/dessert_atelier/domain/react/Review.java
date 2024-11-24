package com.yangnjo.dessert_atelier.domain.react;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Setter(value = AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "display_product_id", nullable = false)
    private DisplayProduct displayProduct;

    @Setter(value = AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "review_image_id")
    private ReviewImage reviewImage;

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

    public static Review createUserReviews(DisplayProduct displayProduct, Member member, ReviewImage reviewImage,
            Integer rate, String comment) {
        Review reviews = new Review();
        reviews.displayProduct = displayProduct;
        reviews.displayProduct.addReview(reviews);
        reviews.member = member;
        member.addReview(reviews);
        reviews.reviewImage = reviewImage;
        reviews.rate = rate;
        reviews.comment = comment;
        reviews.origin = ReviewOrigin.THIS;
        reviews.reviewStatus = ReviewStatus.PUB;
        return reviews;
    }

    public static Review createStoreReviews(DisplayProduct displayProduct, ReviewImage reviewImage,
            Integer rate, String comment, ReviewOrigin origin) {
        Review reviews = new Review();
        reviews.displayProduct = displayProduct;
        reviews.displayProduct.addReview(reviews);
        reviews.reviewImage = reviewImage;
        reviews.rate = rate;
        reviews.comment = comment;
        reviews.origin = origin;
        reviews.reviewStatus = ReviewStatus.PUB;
        return reviews;
    }

}
