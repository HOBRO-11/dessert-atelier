package com.yangnjo.dessert_atelier.db.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> imagesUrl = new ArrayList<>();

    public static ReviewImages createImages(Long id, List<String> imagesUrl) {
        ReviewImages ri = new ReviewImages();
        ri.id = id;
        ri.imagesUrl = imagesUrl;
        return ri;
    }

    protected void addImageUrls(List<String> imageUrls) {
        this.imagesUrl.addAll(imageUrls);
    }

    protected void removeImageUrls(List<String> imageUrls) {
        this.imagesUrl.removeAll(imageUrls);
    }
}
