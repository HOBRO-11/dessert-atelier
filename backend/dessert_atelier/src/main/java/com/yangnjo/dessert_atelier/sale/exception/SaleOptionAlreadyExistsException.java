package com.yangnjo.dessert_atelier.sale.exception;

public class SaleOptionAlreadyExistsException extends RuntimeException {

    public SaleOptionAlreadyExistsException() {
        super();
    }

    public SaleOptionAlreadyExistsException(String message) {
        super(message);
    }

}
