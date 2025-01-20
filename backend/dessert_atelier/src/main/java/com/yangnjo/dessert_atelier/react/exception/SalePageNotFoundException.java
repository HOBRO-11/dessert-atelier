package com.yangnjo.dessert_atelier.react.exception;

public class SalePageNotFoundException extends RuntimeException {
    public SalePageNotFoundException() {
        super();
    }

    public SalePageNotFoundException(String message) {
        super(message);
    }

    public SalePageNotFoundException(Throwable cause) {
        super(cause);
    }

    public SalePageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
