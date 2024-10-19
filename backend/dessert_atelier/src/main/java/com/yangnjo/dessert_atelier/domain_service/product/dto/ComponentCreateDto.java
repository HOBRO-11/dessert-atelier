package com.yangnjo.dessert_atelier.domain_service.product.dto;

import com.yangnjo.dessert_atelier.domain.product.Component;
import com.yangnjo.dessert_atelier.domain.product.ComponentUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComponentCreateDto {
  String name;
  ComponentUnit unit;

  public Component toEntity() {
    return new Component(name, unit);
  }
}
