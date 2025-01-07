package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class ProductQuantityAlreadyExistsException extends RuntimeException {

    public ProductQuantityAlreadyExistsException() {
        super();
    }       

    public ProductQuantityAlreadyExistsException(String message) {
        super(message);
    }

    public ProductQuantityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ProductQuantityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
