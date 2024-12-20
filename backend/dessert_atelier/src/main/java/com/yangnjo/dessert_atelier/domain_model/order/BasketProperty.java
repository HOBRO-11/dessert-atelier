package com.yangnjo.dessert_atelier.domain_model.order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BasketProperty implements Serializable {
    private List<Long> optionIds;
    private Integer quantity;
    private LocalDateTime updatedAt;

    public BasketProperty(List<Long> optionIds) {
        this.optionIds = optionIds;
        this.updatedAt = LocalDateTime.now();
    }

    public void addQuantity(int amount) {
        if (quantity == null && amount > 0) {
            this.quantity = amount;
            return;
        }
        this.quantity += amount;
        this.updatedAt = LocalDateTime.now();
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((optionIds == null) ? 0 : optionIds.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
        BasketProperty other = (BasketProperty) obj;
        if (optionIds == null) {
            if (other.optionIds != null)
                return false;
        } else if (!optionIds.equals(other.optionIds))
            return false;
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

}
