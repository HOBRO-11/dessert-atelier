package com.yangnjo.dessert_atelier.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Type;

import com.yangnjo.dessert_atelier.domain.display_product.Option;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(JsonType.class)
    // @Column(columnDefinition = "JSONB")
    private List<Long> optionIds = new ArrayList<>();

    @Setter
    @Column(nullable = false)
    private Integer quantity;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private CartStatus status;

    public Cart(List<Option> options, Integer quantity, CartStatus status) {
        List<Long> optionIds = options.stream().map(Option::getId).collect(Collectors.toList());
        this.optionIds.addAll(optionIds);
        this.quantity = quantity;
        this.status = status;
    }

}
