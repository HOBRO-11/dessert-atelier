package com.yangnjo.dessert_atelier.repository.product.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QOption;
import com.yangnjo.dessert_atelier.domain_model.product.QProductQuantity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OptionDto {

    private Long id;
    private Long displayProductId;
    private Integer optionLayer;
    private OptionStatus optionStatus;
    private String description;
    private Integer price;
    @Setter
    private List<ProductQuantityDto> productQuantityDtos;

    /*
     * 반드시 setProductQuantityDtos 을 사용하여 DTO 완성을 마무리 짓자.
     * 
     * @see productQuantityDtos
     */
    public OptionDto(Long id, Long displayProductId, Integer optionLayer,
            OptionStatus optionStatus, String description, Integer price) {
        this.id = id;
        this.displayProductId = displayProductId;
        this.optionLayer = optionLayer;
        this.optionStatus = optionStatus;
        this.description = description;
        this.price = price;
    }

    public static Expression<OptionDto> asIncompleteDto() {
        QOption option = QOption.option;
        return Projections.constructor(OptionDto.class,
                option.id,
                option.displayProduct.id,
                option.optionLayer,
                option.optionStatus,
                option.description,
                option.price);
    }

    @Getter
    @AllArgsConstructor
    public static class ProductQuantityDto {
        private Long productId;
        private String name;
        private Integer price;
        private String thumb;
        private Integer quantity;
        private Integer remainQuantity;

        public static Expression<ProductQuantityDto> asDto() {
            QProductQuantity productQuantity = QProductQuantity.productQuantity;
            return Projections.constructor(ProductQuantityDto.class,
                    productQuantity.product.id,
                    productQuantity.product.name,
                    productQuantity.product.price,
                    productQuantity.product.thumb,
                    productQuantity.quantity,
                    productQuantity.product.quantity
                    );
        }
    }
}
