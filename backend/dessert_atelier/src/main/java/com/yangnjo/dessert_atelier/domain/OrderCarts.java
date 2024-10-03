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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCarts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public List<Long> cartIds = new ArrayList<>();

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(mappedBy = "orderCarts", fetch = FetchType.LAZY)
    private Orders orders;

    public OrderCarts(List<Carts> carts) {
        for (Carts cart : carts) {
            this.cartIds.add(cart.getId());
        }
    }

}
