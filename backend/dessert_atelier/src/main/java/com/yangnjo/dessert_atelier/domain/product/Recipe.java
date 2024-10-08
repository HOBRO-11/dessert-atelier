package com.yangnjo.dessert_atelier.domain.product;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe extends BaseEntity {

    @Setter(value = AccessLevel.PROTECTED)
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @JoinColumn(name = "component_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Component component;

    @Setter
    @Column(nullable = false)
    private Integer quantity;

    public Recipe(Component component, Integer quantity) {
        component.addRecipes(List.of(this));
        this.component = component;
        this.quantity = quantity;
    }

}
