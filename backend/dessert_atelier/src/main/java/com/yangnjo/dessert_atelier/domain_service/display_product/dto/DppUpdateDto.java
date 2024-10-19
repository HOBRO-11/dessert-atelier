package com.yangnjo.dessert_atelier.domain_service.display_product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DppUpdateDto {
  Long dppId;
  String naming;
  String content;
  Integer percentDiscount;
}
