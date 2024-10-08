package com.yangnjo.dessert_atelier.domain.product;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Component extends BaseEntity {

    @Setter
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "component")
    private List<Recipe> recipes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ComponentUnit unit;

    public Component(String name, ComponentUnit unit) {
        this.name = name;
        this.unit = unit;
    }

    protected void addRecipes(List<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }

}
