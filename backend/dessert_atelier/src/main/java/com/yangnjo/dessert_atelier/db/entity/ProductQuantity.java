package com.yangnjo.dessert_atelier.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Options options;

    private Integer quantity;

    public static ProductQuantity createProductQuantity(Products product, Options options, Integer quantity) {
        ProductQuantity productQuantity = new ProductQuantity();
        productQuantity.products = product;
        product.addProductQuantity(productQuantity);
        productQuantity.options = options;
        options.addProductQuantity(productQuantity);
        productQuantity.quantity = quantity;
        return productQuantity;
    }

}
