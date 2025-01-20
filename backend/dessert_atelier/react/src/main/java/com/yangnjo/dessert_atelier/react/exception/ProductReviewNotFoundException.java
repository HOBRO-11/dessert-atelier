package com.yangnjo.dessert_atelier.react.exception;

public class ProductReviewNotFoundException extends RuntimeException {
    public ProductReviewNotFoundException() {
        super();
    }

    public ProductReviewNotFoundException(Throwable ex) {
        super(ex);
    }

    public ProductReviewNotFoundException(String message) {
        super(message);
    }

    public ProductReviewNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
