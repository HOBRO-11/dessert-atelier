package com.yangnjo.dessert_atelier.sale.exception;

public class SaleOptionNotFoundException extends RuntimeException {
    public SaleOptionNotFoundException() {
        super();
    }

    public SaleOptionNotFoundException(Throwable ex) {
        super(ex);
    }

    public SaleOptionNotFoundException(String message) {
        super(message);
    }

    public SaleOptionNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }

}
