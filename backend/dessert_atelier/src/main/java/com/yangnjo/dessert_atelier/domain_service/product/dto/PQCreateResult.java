package com.yangnjo.dessert_atelier.domain_service.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PQCreateResult {

    private Integer totalCount;
    private Integer successCount;
    private Integer errorCount;
    private List<PQCreateErrorMessage> errors;

    public PQCreateResult(Integer totalCount, Integer successCount, Integer errorCount,
            List<PQCreateErrorMessage> errors) {
        this.totalCount = totalCount;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.errors = errors;
    }

    public static PQCreateErrorMessage create(Long optionId, Long productId, String message) {
        return new PQCreateErrorMessage(optionId, productId, message);
    }

    public void addCreateResult(PQCreateResult result) {
        this.totalCount += result.getTotalCount();
        this.successCount += result.getSuccessCount();
        this.errorCount += result.getErrorCount();
        this.errors.addAll(result.getErrors());
    }

    @Getter
    @AllArgsConstructor
    public static class PQCreateErrorMessage {
        private Long optionId;
        private Long productId;
        private String message;
    }
}
