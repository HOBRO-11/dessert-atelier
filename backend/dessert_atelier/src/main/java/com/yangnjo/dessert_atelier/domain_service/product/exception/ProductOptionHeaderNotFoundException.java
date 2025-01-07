package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class ProductOptionHeaderNotFoundException extends RuntimeException {

    public ProductOptionHeaderNotFoundException() {
        super();
    }       

    public ProductOptionHeaderNotFoundException(String message) {
        super(message);
    }

    public ProductOptionHeaderNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProductOptionHeaderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
