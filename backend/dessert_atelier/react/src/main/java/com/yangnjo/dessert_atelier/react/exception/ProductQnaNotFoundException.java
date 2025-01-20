package com.yangnjo.dessert_atelier.react.exception;

public class ProductQnaNotFoundException extends RuntimeException {
    public ProductQnaNotFoundException() {
        super();
    }

    public ProductQnaNotFoundException(Throwable ex) {
        super(ex);
    }

    public ProductQnaNotFoundException(String message) {
        super(message);
    }

    public ProductQnaNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
