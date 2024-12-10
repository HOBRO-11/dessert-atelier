package com.yangnjo.dessert_atelier.domain_service.delivery.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DeliveryCreateResult {

    private Integer totalCount;
    private Integer successCount;
    private Integer errorCount;
    private List<DeliveryCreateErrorMessage> errors;

    public DeliveryCreateResult(Integer totalCount, Integer successCount, Integer errorCount,
            List<DeliveryCreateErrorMessage> errors) {
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.errors = errors;
    }

    public static DeliveryCreateErrorMessage create(Long orderCode, String message) {
        return new DeliveryCreateErrorMessage(orderCode, message);
    }

    @Getter
    @AllArgsConstructor
    public static class DeliveryCreateErrorMessage {
        private Long orderCode;
        private String message;
    }
}
