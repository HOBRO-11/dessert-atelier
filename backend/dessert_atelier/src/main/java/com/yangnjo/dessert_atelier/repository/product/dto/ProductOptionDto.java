package com.yangnjo.dessert_atelier.repository.product.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QProductOption;
import com.yangnjo.dessert_atelier.domain_model.product.QProductQuantity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductOptionDto {

    private Long id;
    private Long optionHeaderId;
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
    public ProductOptionDto(Long id, Long optionHeaderId, OptionStatus optionStatus, String description, Integer price) {
        this.id = id;
        this.optionHeaderId = optionHeaderId;
        this.optionStatus = optionStatus;
        this.description = description;
        this.price = price;
    }

    public static Expression<ProductOptionDto> asIncompleteDto() {
        QProductOption productOption = QProductOption.productOption;
        return Projections.constructor(ProductOptionDto.class,
                productOption.id,
                productOption.productOptionHeader.id,
                productOption.optionStatus,
                productOption.description,
                productOption.price);
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
                    productQuantity.product.quantity);
        }
    }
}
