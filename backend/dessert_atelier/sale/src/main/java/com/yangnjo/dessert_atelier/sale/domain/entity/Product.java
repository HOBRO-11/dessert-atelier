package com.yangnjo.dessert_atelier.sale.domain.entity;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Builder
    public Product(String name, String thumbnail, Integer price, Integer stockQuantity) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    

}
