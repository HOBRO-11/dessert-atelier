package com.yangnjo.dessert_atelier.sale.exception;

public class SalePageNotFoundException extends RuntimeException {
    public SalePageNotFoundException() {
        super();
    }

    public SalePageNotFoundException(Throwable ex) {
        super(ex);
    }

    public SalePageNotFoundException(String message) {
        super(message);
    }

    public SalePageNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
