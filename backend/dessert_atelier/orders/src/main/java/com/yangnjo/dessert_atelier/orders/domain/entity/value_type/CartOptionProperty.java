package com.yangnjo.dessert_atelier.orders.domain.entity.value_type;

import java.time.LocalDateTime;
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
public class CartOptionProperty extends OptionProperty implements Comparable<CartOptionProperty> {

    private LocalDateTime updatedAt;

    @Builder
    public CartOptionProperty(Long salePageId, List<Long> optionIds, Integer quantity) {
        super(salePageId, optionIds, quantity);
    }

    @Override
    public void addQuantity(int amount) {
        super.addQuantity(amount);
        updateLocalTime();
    }

    public void updateLocalTime() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public int compareTo(CartOptionProperty o) {
        return o.getUpdatedAt().compareTo(this.updatedAt);
    }

}
