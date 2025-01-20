package com.yangnjo.dessert_atelier.react.exception;

public class ProductReactNotFoundException extends RuntimeException {

    public ProductReactNotFoundException() {
        super();
    }

    public ProductReactNotFoundException(Throwable ex) {
        super(ex);
    }

    public ProductReactNotFoundException(String message) {
        super(message);
    }

    public ProductReactNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
