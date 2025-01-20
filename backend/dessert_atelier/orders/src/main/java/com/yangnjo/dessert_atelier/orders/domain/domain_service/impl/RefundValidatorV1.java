package com.yangnjo.dessert_atelier.orders.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.orders.domain.domain_service.RefundValidator;
import com.yangnjo.dessert_atelier.orders.domain.entity.OrderDetail;
import com.yangnjo.dessert_atelier.orders.domain.entity.Refund;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.OptionProperty;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.OrderOptionProperty;

@Service
public class RefundValidatorV1 implements RefundValidator {

    @Override
    public void validate(OrderDetail orderDetail, OptionProperty optionProperty) throws IllegalArgumentException {
        List<OrderOptionProperty> orderProperties = orderDetail.getProperties();
        List<Refund> refunds = orderDetail.getRefunds();

        int refundedQuantity = 0;

        if (refunds.isEmpty() == false) {
            for (Refund refund : refunds) {
                if (refund.getProperties().contains(optionProperty)) {
                    refundedQuantity += refund.getProperties().stream()
                            .filter(p -> p.equals(optionProperty))
                            .mapToInt(p -> p.getQuantity())
                            .sum();
                }
            }
        }

        for (OrderOptionProperty prop : orderProperties) {
            if (prop.equals(optionProperty)) {
                if (prop.getQuantity() < (optionProperty.getQuantity() + refundedQuantity)) {
                    throw new IllegalArgumentException("Quantity is less than the original quantity");
                }
            }
        }

    }

}
