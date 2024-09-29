package com.yangnjo.dessert_atelier.db.entity;

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
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Users users;

    private List<String> cartIds = new ArrayList<>();

    public static Basket createBasket(Users users) {
        Basket basket = new Basket();
        basket.users = users;
        return basket;
    };

    public void addCartId(String cartId) {
        this.cartIds.add(cartId);
    }

    public void removeCartId(String cartId) {
        this.cartIds.remove(cartId);
    }
}
