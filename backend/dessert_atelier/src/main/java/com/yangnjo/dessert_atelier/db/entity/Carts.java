package com.yangnjo.dessert_atelier.db.entity;

import com.yangnjo.dessert_atelier.db.model.CartStatus;

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

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Products products;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private CartStatus status;

    @JoinColumn(name = "order_code")
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders orders;

    public static Carts createCart(Products products, Integer quantity, Orders orders, CartStatus status) {
        Carts carts = new Carts();
        carts.products = products;
        carts.quantity = quantity;
        carts.status = status;
        carts.orders = orders;
        orders.addCarts(carts);
        return carts;
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
