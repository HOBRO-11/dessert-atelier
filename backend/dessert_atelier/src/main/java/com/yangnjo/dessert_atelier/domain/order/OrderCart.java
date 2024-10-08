package com.yangnjo.dessert_atelier.domain.order;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonType.class)
    // @Column(columnDefinition = "JSONB")
    private List<Long> cartIds;

    public OrderCart(List<Cart> carts) {
        this.cartIds = carts.stream().map(Cart::getId).collect(Collectors.toList());
    }

}
