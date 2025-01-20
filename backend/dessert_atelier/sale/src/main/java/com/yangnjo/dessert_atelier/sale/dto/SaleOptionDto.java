package com.yangnjo.dessert_atelier.sale.dto;

import java.util.List;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.sale.domain.entity.QProductQuantity;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SaleOptionDto {

    private Long id;
    private Long optionHeaderId;
    private SaleOptionStatus optionStatus;
    private String explanation;
    private Integer price;
    @Setter
    private List<ProductQuantityDto> productQuantityDtos;

    /*
     * 반드시 setProductQuantityDtos 을 사용하여 DTO 완성을 마무리 짓자.
     * 
     * @see productQuantityDtos
     */
    public SaleOptionDto(Long id, Long optionHeaderId, SaleOptionStatus optionStatus, String explanation, Integer price) {
        this.id = id;
        this.optionHeaderId = optionHeaderId;
        this.optionStatus = optionStatus;
        this.explanation = explanation;
        this.price = price;
    }

    public static Expression<SaleOptionDto> asIncompleteDto() {
        QSaleOption saleOption = QSaleOption.saleOption;
        return Projections.constructor(SaleOptionDto.class,
                saleOption.id,
                saleOption.saleOptionHeader.id,
                saleOption.explanation,
                saleOption.price);
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
                    productQuantity.product.thumbnail,
                    productQuantity.quantity,
                    productQuantity.product.stockQuantity);
        }
    }
}
