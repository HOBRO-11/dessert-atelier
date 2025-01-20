package com.yangnjo.dessert_atelier.orders.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartOptionPropertyDto {
    private Long salePageId;
    private String title;
    private String thumbnail;
    private List<Long> saleOptionIds;
    private List<String> saleOptionExplanations;
    private Integer quantity;
    private Integer optionPrice;

    @Builder
    public CartOptionPropertyDto(Long salePageId, String title, String thumbnail, List<Long> saleOptionIds,
            List<String> saleOptionExplanations, Integer quantity, Integer optionPrice) {
        this.salePageId = salePageId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.saleOptionIds = saleOptionIds;
        this.saleOptionExplanations = saleOptionExplanations;
        this.quantity = quantity;
        this.optionPrice = optionPrice;
    }

}
