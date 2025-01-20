package com.yangnjo.dessert_atelier.orders.domain.entity.value_type;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OptionProperty implements Serializable {

    private Long salePageId;

    private List<Long> optionIds;

    private Integer quantity;

    public void addQuantity(int amount) {
        if (this.quantity == null && amount > 0) {
            this.quantity = amount;
        } else if (this.quantity == null) {
            return;
        } else if (this.quantity + amount > 0) {
            this.quantity += amount;
        } else {
            return;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((salePageId == null) ? 0 : salePageId.hashCode());
        result = prime * result + ((optionIds == null) ? 0 : optionIds.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OptionProperty other = (OptionProperty) obj;
        if (salePageId == null) {
            if (other.salePageId != null)
                return false;
        } else if (!salePageId.equals(other.salePageId))
            return false;
        if (optionIds == null) {
            if (other.optionIds != null)
                return false;
        } else if (!optionIds.equals(other.optionIds))
            return false;
        return true;
    }

}
