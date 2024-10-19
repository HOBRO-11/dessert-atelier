package com.yangnjo.dessert_atelier.domain.product;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.CascadeType;
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
public class Product extends BaseEntity {

    @Setter
    @Column(unique = true, nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private Integer price;

    @Setter
    @Column(nullable = false)
    private String thumb;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    public Product(String name, int price, String thumb, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.thumb = thumb;
        this.productStatus = ProductStatus.AVAILABLE;
    }

    public void addRecipes(List<Recipe> recipes) {
        for (Recipe r : recipes) {
            this.recipes.add(r);
            r.setProduct(this);
        }
    }

}
