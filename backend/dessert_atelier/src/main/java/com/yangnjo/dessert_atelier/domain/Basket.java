package com.yangnjo.dessert_atelier.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(value = AccessLevel.PROTECTED)
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Users users;

    private List<Long> cartIds = new ArrayList<>();

    public Basket(List<Carts> carts) {
        for (Carts cart : carts) {
            this.cartIds.add(cart.getId());
        }
    }

    public void addCart(Long cartId) {
        this.cartIds.add(cartId);
    }

    public void addCart(Carts cart) {
        this.cartIds.add(cart.getId());
    }

}
