package com.yangnjo.dessert_atelier.orders.domain.entity.value_type;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderOptionProperty extends OptionProperty {

    private boolean isReviewed;

    @Builder
    public OrderOptionProperty(Long salePageId, List<Long> optionIds, Integer quantity) {
        super(salePageId, optionIds, quantity);
        this.isReviewed = false;
    }

    public void setReviewed() {
        this.isReviewed = true;
    }

}
