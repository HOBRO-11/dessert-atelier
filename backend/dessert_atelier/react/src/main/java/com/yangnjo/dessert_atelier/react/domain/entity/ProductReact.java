package com.yangnjo.dessert_atelier.react.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_PRODUCT_REACT_NAME", columnNames = {"name"}))
@NoArgsConstructor
public class ProductReact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder
    public ProductReact(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "productReact")
    private List<ProductQna> productQnas = new ArrayList<>();

    @OneToMany(mappedBy = "productReact")
    private List<ProductReview> productReviews = new ArrayList<>();

    protected void addProductQnA(ProductQna productQna) {
        this.productQnas.add(productQna);
    }

    protected void addProductReview(ProductReview productReview) {
        this.productReviews.add(productReview);
    }

}
