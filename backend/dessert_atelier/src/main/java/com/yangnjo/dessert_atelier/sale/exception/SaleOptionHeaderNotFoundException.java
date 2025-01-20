package com.yangnjo.dessert_atelier.sale.exception;

public class SaleOptionHeaderNotFoundException extends RuntimeException {

    public SaleOptionHeaderNotFoundException() {
        super();
    }       

    public SaleOptionHeaderNotFoundException(String message) {
        super(message);
    }

    public SaleOptionHeaderNotFoundException(Throwable cause) {
        super(cause);
    }

    public SaleOptionHeaderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
