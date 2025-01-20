package com.yangnjo.dessert_atelier.orders.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class OrderProperties {
    
    @Value("${domain.order.cart_option.max_count}")
    private int maxCount;

    @Value("${domain.order.order_detail.subfix_length}")
    private int subfixLength;

    @Value("${domain.order.order_detail.try_count}")
    private int tryCount;
}
