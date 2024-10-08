package com.yangnjo.dessert_atelier.domain.display_product;

import java.util.List;

import jakarta.persistence.CascadeType;
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
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(value = AccessLevel.PROTECTED)
    @JoinColumn(name = "display_product_preset_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DisplayProductPreset displayProductPreset;

    @Setter
    private Integer totalQuantity;

    private Integer optionLayer;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private OptionStatus optionStatus;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "options", cascade = CascadeType.REMOVE)
    private List<ProductQuantity> productQuantities;

    /*
     * totalQuantity set infinitely, if totalQuantity is null
     */
    public Option(Integer totalQuantity, String description, Integer price, List<ProductQuantity> productQuantities, Integer optionLayer) {
        this.totalQuantity = totalQuantity;
        this.optionLayer = optionLayer;
        this.description = description;
        this.price = price;
        this.optionStatus = OptionStatus.AVAILABLE;
        this.productQuantities = productQuantities;
        productQuantities.forEach(productQuantity -> productQuantity.setOptions(this));
    }

}
