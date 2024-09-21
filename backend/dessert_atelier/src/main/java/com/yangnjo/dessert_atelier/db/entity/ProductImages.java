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
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> imagesUrl = new ArrayList<>();

    public static ProductImages createImages(Long id, List<String> imagesUrl) {
        ProductImages pi = new ProductImages();
        pi.id = id;
        pi.imagesUrl = imagesUrl;
        return pi;
    }

    protected void addImageUrls(List<String> imageUrls) {
        this.imagesUrl.addAll(imageUrls);
    }

    protected void removeImageUrls(List<String> imageUrls) {
        this.imagesUrl.removeAll(imageUrls);
    }
}
