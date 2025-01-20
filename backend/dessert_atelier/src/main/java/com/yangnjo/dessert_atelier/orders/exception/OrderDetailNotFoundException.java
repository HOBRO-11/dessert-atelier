package com.yangnjo.dessert_atelier.orders.exception;

public class OrderDetailNotFoundException extends RuntimeException{

    public OrderDetailNotFoundException() {
        super();
    }

    public OrderDetailNotFoundException(Throwable ex) {
        super(ex);
    }

    public OrderDetailNotFoundException(String message) {
        super(message);
    }

    public OrderDetailNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
