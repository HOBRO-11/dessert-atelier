package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DeliveryUpdateResult {

    private Integer totalCount;
    private Integer successCount;
    private Integer errorCount;
    private List<DeliveryUpdateErrorMessage> errors;

    public DeliveryUpdateResult(Integer totalCount, Integer successCount, Integer errorCount,
            List<DeliveryUpdateErrorMessage> errors) {
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.errors = errors;
    }

    public static DeliveryUpdateErrorMessage create(String deliveryCode, String message) {
        return new DeliveryUpdateErrorMessage(deliveryCode, message);
    }

    @Getter
    @AllArgsConstructor
    public static class DeliveryUpdateErrorMessage {
        private String deliveryCode;
        private String message;
    }
}
