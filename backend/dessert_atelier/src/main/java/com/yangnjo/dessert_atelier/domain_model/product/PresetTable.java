package com.yangnjo.dessert_atelier.domain_model.product;

import com.yangnjo.dessert_atelier.domain_model.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PresetTable extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private DisplayProduct displayProduct;

    @Setter
    private Integer numbering;

    public PresetTable(DisplayProduct displayProduct) {
        this.displayProduct = displayProduct;
    }
}
