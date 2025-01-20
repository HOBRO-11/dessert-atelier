package com.yangnjo.dessert_atelier.sale.exception;

public class ProductQuantityNotFoundException extends RuntimeException{

    public ProductQuantityNotFoundException() {
        super();
    }

    public ProductQuantityNotFoundException(Throwable ex) {
        super(ex);
    }

    public ProductQuantityNotFoundException(String message) {
        super(message);
    }

    public ProductQuantityNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
