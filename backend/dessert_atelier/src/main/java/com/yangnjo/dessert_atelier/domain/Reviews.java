package com.yangnjo.dessert_atelier.domain;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

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
public class Reviews extends BaseEntity {

    @Setter(value = AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_product_id", nullable = false)
    private DisplayProducts displayProducts;

    @Setter(value = AccessLevel.PROTECTED)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_image_id", unique = false)
    private ReviewImages reviewImages;

    @Column(nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    private ReviewOrigin origin;

    public static Reviews createUserReviews(DisplayProducts displayProducts, Users users, ReviewImages reviewImages, String comment) {
        Reviews reviews = new Reviews();
        reviews.users = users;
        reviews.reviewImages = reviewImages;
        reviews.comment = comment;
        reviews.origin = ReviewOrigin.THIS;
        displayProducts.addReview(reviews);
        users.addReview(reviews);
        return reviews;
    }

    public static Reviews createStoreReviews(DisplayProducts displayProducts, ReviewImages reviewImages, String comment, ReviewOrigin origin) {
        Reviews reviews = new Reviews();
        reviews.displayProducts = displayProducts;
        displayProducts.addReview(reviews);
        reviews.reviewImages = reviewImages;
        reviews.comment = comment;
        reviews.origin = origin;
        return reviews;
    }

    public void changeComment(String newComment) {
        this.comment = newComment;
    }

}
