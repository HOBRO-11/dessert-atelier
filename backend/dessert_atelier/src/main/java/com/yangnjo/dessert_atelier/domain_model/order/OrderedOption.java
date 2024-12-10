package com.yangnjo.dessert_atelier.domain_model.order;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderedOption {
    private List<Long> optionIds;
    private Integer quantity;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        OrderedOption other = (OrderedOption) obj;
        if (optionIds == null) {
            if (other.optionIds != null)
                return false;
        } else if (!optionIds.equals(other.optionIds))
            return false;
        return true;
    }

}
