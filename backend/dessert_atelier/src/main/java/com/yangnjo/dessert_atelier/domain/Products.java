package com.yangnjo.dessert_atelier.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Products extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String thumb;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "products", orphanRemoval = true)
    private List<Recipes> recipes = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "products")
    private List<ProductQuantity> productQuantities = new ArrayList<>();

    public static Products createProduct(String name, int price,
            String thumb, String comment, ProductStatus status) {
        Products products = new Products();
        products.name = name;
        products.price = price;
        products.thumb = thumb;
        products.status = ProductStatus.AVAILABLE;
        return products;
    }

    public void changeName(String name) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
    }

    public void changeThumb(String thumb) {
        this.thumb = thumb;
    }

    protected void addRecipes(Recipes recipes) {
        this.recipes.add(recipes);
    }

    public void subtractRecipes(Recipes recipes) {
        this.recipes.remove(recipes);
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

    protected void addProductQuantity(ProductQuantity productQuantity) {
        this.productQuantities.add(productQuantity);
    }
}
