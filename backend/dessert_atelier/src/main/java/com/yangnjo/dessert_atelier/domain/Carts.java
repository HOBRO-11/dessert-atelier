package com.yangnjo.dessert_atelier.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "display_product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DisplayProducts displayProducts;

    @JoinColumn(name = "option_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Options options;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CartStatus status;

    public Carts(DisplayProducts displayProducts, Options options, Integer quantity, CartStatus status) {
        this.displayProducts = displayProducts;
        displayProducts.addCart(this);
        this.options = options;
        options.addCart(this);
        this.quantity = quantity;
        this.status = status;
    }

    public Integer changeQuantity(int newQuantity) {
        if (newQuantity < 1) {
            return null;
        }
        this.quantity += newQuantity;
        return quantity;
    }

    public void used() {
        this.status = CartStatus.USED;
    }

    public void waiting() {
        this.status = CartStatus.WAITING;
    }
}
