package com.yangnjo.dessert_atelier.domain_model.product;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain_model.model.BaseEntity;
import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
import com.yangnjo.dessert_atelier.domain_model.react.QnA;
import com.yangnjo.dessert_atelier.domain_model.react.Review;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayProduct extends BaseEntity {

    @Setter
    @Column(nullable = false, unique = true)
    private String title;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    private Integer optionLayer;

    @Setter
    @Column(nullable = false)
    private String thumb;

    @Type(JsonType.class)
    private List<ImageSrc> images;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private DisplayProductStatus displayProductStatus;

    @OneToMany(mappedBy = "displayProduct")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "displayProduct")
    private List<QnA> qnas = new ArrayList<>();

    public DisplayProduct(String title, String desc, String thumb, List<ImageSrc> images) {
        this.title = title;
        this.description = desc;
        this.thumb = thumb;
        this.images = images;
        this.displayProductStatus = DisplayProductStatus.PREPARE;
    }

    public void addReview(Review reviews) {
        this.reviews.add(reviews);
    }

    public void addQna(QnA qnas) {
        this.qnas.add(qnas);
    }
}
