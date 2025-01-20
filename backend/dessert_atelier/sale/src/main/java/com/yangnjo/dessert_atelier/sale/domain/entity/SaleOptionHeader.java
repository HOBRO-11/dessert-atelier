package com.yangnjo.dessert_atelier.sale.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_SALE_OPTION_HEADER", columnNames = { "display_product_id", "option_header_name" }))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class SaleOptionHeader extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SalePage salePage;

    @Setter
    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SaleOptionHeaderType headerType;

    @OneToMany(mappedBy = "saleOptionHeader")
    private List<SaleOption> saleOptions = new ArrayList<>();

    @Builder
    public SaleOptionHeader(SalePage salePage, String header, SaleOptionHeaderType headerType) {
        this.salePage = salePage;
        this.salePage.addOptionHeader(this);
        this.header = header;
        this.headerType = headerType;
    }

    protected void addSaleOption(SaleOption saleOption) {
        this.saleOptions.add(saleOption);
    }

}
