package com.yangnjo.dessert_atelier.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain.member.Member;

import io.hypersistence.utils.hibernate.type.json.JsonType;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Type(JsonType.class)
    // @Column(columnDefinition = "JSONB")
    private List<Long> cartIds = new ArrayList<>();

    public Basket(Member member) {
        this.member = member;
        member.setBasket(this);
    }

    public void addCarts(List<Cart> carts) {
        List<Long> cartIds = carts.stream().map(Cart::getId).collect(Collectors.toList());
        this.cartIds.addAll(cartIds);
    }

    public void removeCarts(List<Cart> carts) {
        List<Long> cartIds = carts.stream().map(Cart::getId).collect(Collectors.toList());
        this.cartIds.removeAll(cartIds);
    }

}
