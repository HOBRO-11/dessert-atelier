package com.yangnjo.dessert_atelier.domain_service.display_product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductUpdateDto {
  Long dpId;
  String naming;
  String description;
  String thumb;
}
