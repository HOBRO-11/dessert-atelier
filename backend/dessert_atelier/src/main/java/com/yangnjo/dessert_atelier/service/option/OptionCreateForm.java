package com.yangnjo.dessert_atelier.service.option;

import java.util.List;
import java.util.stream.Collectors;

import com.yangnjo.dessert_atelier.domain_service.product.dto.OptionCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductQuantityCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionCreateForm {
    Long dpId;
    String description;
    Integer price;
    Integer optionLayer;
    List<ProductQuantityCreateForm> productQuantityForms;

    public OptionCreateDto toDto() {
        return new OptionCreateDto(dpId, description, price, optionLayer);
    }

    public List<ProductQuantityCreateDto> toProductQuantityDtos(Long optionId) {
        return productQuantityForms.stream()
                .map(pqf -> pqf.toProductQuantityDto(optionId))
                .collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class ProductQuantityCreateForm {
        Long productId;
        Integer quantity;

        public ProductQuantityCreateDto toProductQuantityDto(Long optionId) {
            return new ProductQuantityCreateDto(productId, optionId, quantity);
        }
    }
}
