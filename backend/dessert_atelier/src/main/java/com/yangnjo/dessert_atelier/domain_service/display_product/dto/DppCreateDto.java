package com.yangnjo.dessert_atelier.domain_service.display_product.dto;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPresetImage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DppCreateDto {
  Long dpId;
  String naming;
  String thumb;
  String title;
  Integer price;
  Integer optionLayer;
  String content;
  DisplayProductPresetImage dppImg;
  @Setter
  Integer percentDiscount;
  LocalDateTime startDateTime;
  LocalDateTime endDateTime;

  @Builder
  public DppCreateDto(Long dpId, String naming, String thumb, String title, Integer price, Integer optionLayer,
      String content, DisplayProductPresetImage dppImg) {
    this.dpId = dpId;
    this.naming = naming;
    this.thumb = thumb;
    this.title = title;
    this.price = price;
    this.optionLayer = optionLayer;
    this.content = content;
    this.dppImg = dppImg;
  }

  public void setPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
  }

  public DisplayProductPreset toDefaultDppEntity(DisplayProduct dp) {
    return DisplayProductPreset.createDefaultDPP(dp, naming, thumb, title, price, optionLayer, content, dppImg);
  }

  public DisplayProductPreset toCustomDppEntity(DisplayProduct dp) {
    return DisplayProductPreset.createCustomDPP(dp, naming, thumb, title, price, optionLayer, content, dppImg,
        percentDiscount, startDateTime, endDateTime);
  }

}
