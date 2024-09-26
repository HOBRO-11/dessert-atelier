package com.yangnjo.dessert_atelier.db.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.db.model.ReviewOrigin;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "review_image_id")
    private ReviewImages reviewImages;

    @Column(nullable = false)
    private String comment;

    private LocalDateTime reviewUpdatedAt;

    private String react;

    private LocalDateTime reactUpdatedAt;

    @Enumerated(EnumType.STRING)
    private ReviewOrigin origin;

    public Reviews createReviews(Products products, Users users, ReviewImages reviewImages, String comment, ReviewOrigin origin) {
        Reviews reviews = new Reviews();
        reviews.products = products;
        products.addReview(reviews);
        reviews.users = users;
        users.addReview(reviews);
        reviews.reviewImages = reviewImages;
        reviews.comment = comment;
        setReviewUpdateAt();
        reviews.origin = origin;
        return reviews;
    }

    
    public void addImages(List<String> newImageUrls) {
        this.reviewImages.addImageUrls(newImageUrls);
        setReviewUpdateAt();
    }
    
    public void removeImages(List<String> images) {
        this.reviewImages.removeImageUrls(images);
        setReviewUpdateAt();
    }
    
    public void changeComment(String newComment){
        this.comment = newComment;
        setReviewUpdateAt();
    }

    public void react(String react){
        this.react = react;
        setReactUpdateAt();
    }

    private void setReviewUpdateAt(){
        this.reviewUpdatedAt = LocalDateTime.now();
    }

    private void setReactUpdateAt(){
        this.reactUpdatedAt = LocalDateTime.now();
    }
}