package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class PresetTableAlreadyException extends RuntimeException {
    public PresetTableAlreadyException() {
        super();
    }

    public PresetTableAlreadyException(Throwable ex) {
        super(ex);
    }

    public PresetTableAlreadyException(String message) {
        super(message);
    }

    public PresetTableAlreadyException(String message, Throwable ex) {
        super(message, ex);
    }
}
