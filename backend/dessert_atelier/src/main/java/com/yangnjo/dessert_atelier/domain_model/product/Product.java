package com.yangnjo.dessert_atelier.domain_model.product;

import com.yangnjo.dessert_atelier.domain_model.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Integer quantity;

    @Setter
    @Column(nullable = false)
    private String thumb;

    public Product(String name, int price, String thumb, Integer quantity) {
        this.name = name;
        this.price = price;
        this.thumb = thumb;
        this.quantity = quantity;
    }
}
