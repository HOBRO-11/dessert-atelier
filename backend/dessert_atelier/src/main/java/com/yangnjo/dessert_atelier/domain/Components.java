package com.yangnjo.dessert_atelier.domain;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Components extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "components")
    private List<Recipes> recipes;

    public static Components createComponent(String name) {
        Components components = new Components();
        components.name = name;
        return components;
    }

    public void changeName(String name){
        this.name = name;
    }

    protected void addRecipes(Recipes recipes){
        this.recipes.add(recipes);
    }

    protected void removeRecipes(Recipes recipes){
        this.recipes.remove(recipes);
    }

}
