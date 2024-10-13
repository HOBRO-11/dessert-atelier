package com.yangnjo.dessert_atelier.domain.display_product;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;
import com.yangnjo.dessert_atelier.domain.react.QnA;
import com.yangnjo.dessert_atelier.domain.react.Review;

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
    private String naming;

    @Setter
    @Column(nullable = false)
    private String thumb;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    private Long currentDppId;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private SaleStatus saleStatus;

    @OneToMany(mappedBy = "displayProduct")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "displayProduct")
    private List<QnA> qnas = new ArrayList<>();

    @OneToMany(mappedBy = "displayProduct")
    private List<DisplayProductPreset> displayProductPresets = new ArrayList<>();

    public DisplayProduct(String naming, String desc, String thumb, SaleStatus saleStatus) {
        this.naming = naming;
        this.description = desc;
        this.thumb = thumb;
        this.saleStatus = saleStatus;
    }

    public void addReview(Review reviews) {
        this.reviews.add(reviews);
    }

    public void addQna(QnA qnas) {
        this.qnas.add(qnas);
    }

    public void addDisplayProductPreset(DisplayProductPreset displayProductPreset) {
        this.displayProductPresets.add(displayProductPreset);
    }

}
