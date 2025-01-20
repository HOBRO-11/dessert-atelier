package com.yangnjo.dessert_atelier.sale.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(Throwable ex) {
        super(ex);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
