package com.yangnjo.dessert_atelier.domain;

import java.util.ArrayList;
import java.util.List;

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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(value = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "options")
    private List<Carts> carts = new ArrayList<>();

    public Options(Integer totalQuantity, String description, Integer price, List<ProductQuantity> productQuantities) {
        this.totalQuantity = totalQuantity;
        this.description = description;
        this.price = price;
        this.status = OptionStatus.AVAILABLE;
        this.productQuantities = productQuantities;
        productQuantities.forEach(productQuantity -> productQuantity.setOptions(this));
    }

    public void setStatusAvailable() {
        this.status = OptionStatus.AVAILABLE;
    }

    public void setStatusUnavailable() {
        this.status = OptionStatus.UNAVAILABLE;
    }

    protected void addCart(Carts carts) {
        this.carts.add(carts);
    }

}
