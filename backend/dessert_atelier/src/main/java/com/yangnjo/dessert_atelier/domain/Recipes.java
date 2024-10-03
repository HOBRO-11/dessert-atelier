package com.yangnjo.dessert_atelier.domain;

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
public class Recipes extends BaseEntity {

    @Setter(value = AccessLevel.PROTECTED)
    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Products products;

    @JoinColumn(name = "component_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Components components;

    @Column(nullable = false)
    private Integer quantity;

    public Recipes(Components components, Integer quantity) {
        components.addRecipes(this);
        this.components = components;
        this.quantity = quantity;
    }

    public int addQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity += quantity;
        }
        return this.quantity;
    }

    public int subtractQuantity(int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
        }
        return this.quantity;
    }

}
