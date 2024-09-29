package com.yangnjo.dessert_atelier.db.entity;

import java.util.List;

import com.yangnjo.dessert_atelier.db.model.BaseEntity;
import com.yangnjo.dessert_atelier.db.model.ReviewOrigin;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reviews extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_product_id", nullable = false)
    private DisplayProducts displayProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_image_id")
    private ReviewImages reviewImages;

    @Column(nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    private ReviewOrigin origin;

    public Reviews createReviews(DisplayProducts dp ,Users users, ReviewImages reviewImages, String comment, ReviewOrigin origin) {
        Reviews reviews = new Reviews();
        reviews.displayProducts = dp;
        dp.addReview(reviews);
        reviews.users = users;
        users.addReview(reviews);
        reviews.reviewImages = reviewImages;
        reviews.comment = comment;
        reviews.origin = origin;
        return reviews;
    }

    public void addImages(List<String> newImageUrls) {
        this.reviewImages.addImageUrls(newImageUrls);
    }
    
    public void removeImages(List<String> images) {
        this.reviewImages.removeImageUrls(images);
    }
    
    public void changeComment(String newComment){
        this.comment = newComment;
    }

}
