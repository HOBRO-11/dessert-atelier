package com.yangnjo.dessert_atelier.domain_service.order.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantity;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantityStatus;
import com.yangnjo.dessert_atelier.domain.order.Orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionQuantityCreateDto {
  Long orderCode;
  Long dppId;
  List<Long> optionIds;
  Integer quantity;

  public OptionQuantity toEntity(Orders order, DisplayProductPreset dpp, List<Option> options) {
    return new OptionQuantity(order, options, dpp, quantity, OptionQuantityStatus.PAID);
  }
}
