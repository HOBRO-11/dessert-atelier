package com.yangnjo.dessert_atelier.domain_model.product;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionHeader extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DisplayProduct displayProduct;

    @Setter
    @Column(nullable = false)
    private String optionHeaderName;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OptionHeaderType headerType;

    @OneToMany(mappedBy = "productOptionHeader")
    private List<ProductOption> options;

    public ProductOptionHeader(DisplayProduct displayProduct, String optionHeaderName, OptionHeaderType headerType) {
        this.displayProduct = displayProduct;
        this.displayProduct.addOptionHeader(this);
        this.optionHeaderName = optionHeaderName;
        this.headerType = headerType;
    }
    
}
