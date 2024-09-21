package com.yangnjo.dessert_atelier.db.entity;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.db.model.BaseEntity;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status = ProductStatus.SALE;

    @Column(nullable = false)
    private String thumb;
    
    @Column
    private String comment;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToOne(fetch = FetchType.LAZY)
    private ProductImages images;

    public static Products createProduct(String name, Integer price, Integer quantity, ProductStatus status,
            String thumb, String comment,
            ProductImages images) {
        Products products = new Products();
        products.name = name;
        products.price = price;
        products.quantity = quantity;
        products.status = status;
        products.thumb = thumb;
        products.comment = comment;
        products.images = images;
        return products;
    }

    public void setSale() {
        this.status = ProductStatus.SALE;
    }

    public void setSoldOut() {
        this.status = ProductStatus.SOLD_OUT;
    }

    public void setHide() {
        this.status = ProductStatus.HIDE;
    }

    public void modify(String name, int price, String comment) {
        if (price > 0) {
            this.price = price;
        }
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        if (StringUtils.hasText(comment)) {
            this.comment = comment;
        }
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

    public void addImages(ProductImages images) {
        List<String> newImages = images.getImagesUrl();
        this.images.getImagesUrl().addAll(newImages);
    }

    public void removeImages(ProductImages images) {
        List<String> newImages = images.getImagesUrl();
        this.images.getImagesUrl().removeAll(newImages);
    }

    public void changeThumb(String thumb) {
        this.thumb = thumb;
    }
}
