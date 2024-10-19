package com.yangnjo.dessert_atelier.domain_service.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecipeCreateDto {
  Long productId;
  List<ComponentQuantity> componentQuantities;

  @Getter
  @AllArgsConstructor
  public static class ComponentQuantity{
    Long componentId;
    Integer quantity;
  }
}
