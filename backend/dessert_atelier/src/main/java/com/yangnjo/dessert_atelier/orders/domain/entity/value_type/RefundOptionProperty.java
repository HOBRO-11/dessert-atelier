package com.yangnjo.dessert_atelier.orders.domain.entity.value_type;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class RefundOptionProperty extends OptionProperty {

    @Builder
    public RefundOptionProperty(Long salePageId, List<Long> optionIds, Integer quantity) {
        super(salePageId, optionIds, quantity);
    }
}
