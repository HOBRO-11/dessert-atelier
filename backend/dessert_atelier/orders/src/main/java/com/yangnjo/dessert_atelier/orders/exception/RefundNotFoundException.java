package com.yangnjo.dessert_atelier.orders.exception;

public class RefundNotFoundException extends RuntimeException {

    public RefundNotFoundException() {
        super();
    }

    public RefundNotFoundException(Throwable ex) {
        super(ex);
    }

    public RefundNotFoundException(String message) {
        super(message);
    }

    public RefundNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
