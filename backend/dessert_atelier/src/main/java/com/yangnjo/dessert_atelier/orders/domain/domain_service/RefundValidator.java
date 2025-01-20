package com.yangnjo.dessert_atelier.orders.domain.domain_service;

import com.yangnjo.dessert_atelier.orders.domain.entity.OrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.OptionProperty;

public interface RefundValidator {
    
    public void validate(OrderDetail orderDetail, OptionProperty optionProperty) throws IllegalArgumentException;

}
