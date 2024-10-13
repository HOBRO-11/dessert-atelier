package com.yangnjo.dessert_atelier.repository.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.domain.display_product.QProductQuantity;

import static com.yangnjo.dessert_atelier.domain.display_product.QOption.*;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public class OptionDetailDto {

  private Long id;
  private Integer totalQuantity;
  private Integer optionLayer;
  private OptionStatus optionStatus;
  private String description;
  private Integer price;
  @Setter
  private List<ProductQuantityDto> productQuantityDtos;

  /*
   * 반드시 setProductQuantityDtos 을 사용하여 DTO 완성을 마무리 짓자.
   * @see productQuantityDtos
   */
  public OptionDetailDto(Long id, Integer totalQuantity, Integer optionLayer, OptionStatus optionStatus,
      String description, Integer price) {
    this.id = id;
    this.totalQuantity = totalQuantity;
    this.optionLayer = optionLayer;
    this.optionStatus = optionStatus;
    this.description = description;
    this.price = price;
  }

  public static Expression<OptionDetailDto> asIncompleteDto() {
    return Projections.constructor(OptionDetailDto.class,
        option.id,
        option.totalQuantity,
        option.optionLayer,
        option.optionStatus,
        option.description,
        option.price
        );
  }

  @Getter
  @ToString
  @AllArgsConstructor
  public static class ProductQuantityDto {
    private Long productId;
    private String name;
    private String thumb;
    private Integer price;
    private ProductStatus productStatus;
    private Integer quantity;

    public static Expression<ProductQuantityDto> asDto() {
      QProductQuantity productQuantity = QProductQuantity.productQuantity;
      return Projections.constructor(ProductQuantityDto.class,
          productQuantity.product.id,
          productQuantity.product.name,
          productQuantity.product.thumb,
          productQuantity.product.price,
          productQuantity.product.productStatus,
          productQuantity.quantity);
    }
  }
}
