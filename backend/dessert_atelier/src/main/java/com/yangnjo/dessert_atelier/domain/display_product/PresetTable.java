package com.yangnjo.dessert_atelier.domain.display_product;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
  @JoinColumn(name = "default_dpp_id")
  @OneToOne(fetch = FetchType.LAZY)
  private DisplayProductPreset defaultDpp;

  @Setter
  @JoinColumn(name = "current_dpp_id")
  @OneToOne(fetch = FetchType.LAZY)
  private DisplayProductPreset currentDpp;

  @Setter
  private Integer numbering;

  public PresetTable(DisplayProduct displayProduct) {
    this.displayProduct = displayProduct;
  }

}
