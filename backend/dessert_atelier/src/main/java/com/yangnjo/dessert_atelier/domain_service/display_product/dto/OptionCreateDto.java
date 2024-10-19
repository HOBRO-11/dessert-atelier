package com.yangnjo.dessert_atelier.domain_service.display_product.dto;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionCreateDto {
  Long dppId;
  Integer totalQuantity;
  String description;
  Integer price;
  Integer optionLayer;

  public Option toEntity(DisplayProductPreset dpp) {
    return new Option(dpp, totalQuantity, description, price, optionLayer);
  }
}
