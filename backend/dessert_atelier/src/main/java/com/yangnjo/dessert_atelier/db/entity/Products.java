package com.yangnjo.dessert_atelier.db.entity;

import com.yangnjo.dessert_atelier.db.model.BaseEntity;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Products extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.SALE;

    @Column(nullable = false)
    private String thumb;

    public static Products createProduct(String name, ProductStatus status, Integer price, String thumb) {
        Products products = new Products();
        products.name = name;
        products.status = status;
        products.price = price;
        products.thumb = thumb;
        return products;
    }

    public void setSale() {
        this.status = ProductStatus.SALE;
    }

    public void setSoldOut() {
        this.status = ProductStatus.SOLD_OUT;
    }

    public void setHide() {
        this.status = ProductStatus.HIDE;
    }

    public void modify(String name, int price, String thumb, ProductStatus status) {
        if (price != 0) {
            this.price = price;
        }
        if (name != null) {
            this.name = name;
        }
        if (thumb != null) {
            this.thumb = thumb;
        }
        if (status != null) {
            this.status = status;
        }
    }

}
