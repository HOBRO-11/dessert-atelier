package com.yangnjo.dessert_atelier.db.entity;

import com.yangnjo.dessert_atelier.db.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipes extends BaseEntity {

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Products products;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Components components;

    @Column(nullable = false)
    private Integer quantity;

    public static Recipes createRecipes(Products products, Components components, Integer quantity) {
        Recipes recipes = new Recipes();
        recipes.products = products;
        products.addRecipes(recipes);
        recipes.components = components;
        recipes.quantity = quantity;
        return recipes;
    }

    public void changeComponent(Components components) {
        this.components.removeRecipes(this);
        this.components = components;
        components.addRecipes(this);
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
