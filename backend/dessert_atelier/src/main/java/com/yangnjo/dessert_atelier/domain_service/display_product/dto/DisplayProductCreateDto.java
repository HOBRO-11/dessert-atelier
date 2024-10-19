package com.yangnjo.dessert_atelier.domain_service.display_product.dto;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductCreateDto {
  String naming;
  String desc;
  String thumb;
  SaleStatus saleStatus;

  public DisplayProduct toEntity() {
    return new DisplayProduct(naming, desc, thumb, saleStatus);
  }
}
