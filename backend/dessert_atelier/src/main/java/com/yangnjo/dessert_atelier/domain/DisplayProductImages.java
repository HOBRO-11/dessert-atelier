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
public class DisplayProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<String> imagesUrl = new ArrayList<>();

    public DisplayProductImages(List<String> imagesUrl) {
        this.imagesUrl.addAll(imagesUrl);
    }

    protected void addImageUrls(List<String> imageUrls) {
        this.imagesUrl.addAll(imageUrls);
    }

    protected void removeImageUrls(List<String> imageUrls) {
        this.imagesUrl.removeAll(imageUrls);
    }

}
