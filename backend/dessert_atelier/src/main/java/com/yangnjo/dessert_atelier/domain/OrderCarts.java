package com.yangnjo.dessert_atelier.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCarts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public List<Long> cartIds = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "orderCarts")
    private Orders orders;

    public static OrderCarts createOrderCarts(List<Carts> carts) {
        OrderCarts orderCarts = new OrderCarts();
        for (Carts cart : carts) {
            orderCarts.cartIds.add(cart.getId());
        }
        return orderCarts;
    }

    public static OrderCarts createOrderCart(List<Long> cartIds) {
        OrderCarts orderCarts = new OrderCarts();
        orderCarts.cartIds = cartIds;
        return orderCarts;
    }

}
