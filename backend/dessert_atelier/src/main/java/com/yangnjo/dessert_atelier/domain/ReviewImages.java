package com.yangnjo.dessert_atelier.domain;

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

    public ReviewImages(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }

    public void addImageUrls(List<String> imageUrls) {
        this.imagesUrl.addAll(imageUrls);
    }

    public void removeImageUrls(List<String> imageUrls) {
        this.imagesUrl.removeAll(imageUrls);
    }
}
