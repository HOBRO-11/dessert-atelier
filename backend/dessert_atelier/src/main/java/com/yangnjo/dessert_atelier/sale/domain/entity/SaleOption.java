package com.yangnjo.dessert_atelier.sale.domain.entity;

import java.util.ArrayList;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "sale_option_header_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SaleOptionHeader saleOptionHeader;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private SaleOptionStatus status;

    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "saleOption", cascade = CascadeType.REMOVE)
    private List<ProductQuantity> productQuantities = new ArrayList<>();

    @Builder
    public SaleOption(SaleOptionHeader saleOptionHeader, SaleOptionStatus saleOptionStatus, String explanation, Integer price) {
        this.saleOptionHeader = saleOptionHeader;
        this.saleOptionHeader.addSaleOption(this);
        this.status = saleOptionStatus;
        this.explanation = explanation;
        this.price = price;
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

    protected void addProductQuantity(ProductQuantity productQuantity) {
        this.productQuantities.add(productQuantity);
    }

}
