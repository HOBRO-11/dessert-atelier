package com.yangnjo.dessert_atelier.db.entity;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.db.model.OptionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "display_product_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DisplayProducts displayProducts;

    @OneToMany(mappedBy = "options")
    private List<ProductQuantity> productQuantities;

    private Integer totalQuantity;

    @Enumerated(value = EnumType.STRING)
    private OptionStatus status;

    @Column(nullable = false)
    private String description;

    // TODO: 옵션 가격 추가
    private Integer price;

    @OneToMany(mappedBy = "options")
    private List<Carts> carts = new ArrayList<>();

    public static Options createOptions(DisplayProducts displayProducts, List<ProductQuantity> productQuantities,
            Integer totalQuantity, String description, Integer price) {
        Options options = new Options();
        options.displayProducts = displayProducts;
        displayProducts.addOption(options);
        options.productQuantities.addAll(productQuantities);
        options.totalQuantity = totalQuantity;
        options.description = description;
        options.price = price;
        options.status = OptionStatus.AVAILABLE;
        return options;
    }

    public void setStatusAvailable() {
        this.status = OptionStatus.AVAILABLE;
    }

    public void setStatusUnavailable() {
        this.status = OptionStatus.UNAVAILABLE;
    }

    public void addCart(Carts carts) {
        this.carts.add(carts);
    }

    protected void addProductQuantity(ProductQuantity productQuantity) {
        this.productQuantities.add(productQuantity);
    }

}
