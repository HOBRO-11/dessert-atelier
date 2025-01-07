package com.yangnjo.dessert_atelier.domain_service.product.exception;

public class OptionAlreadyExistsException extends RuntimeException {

    public OptionAlreadyExistsException() {
        super();
    }       

    public OptionAlreadyExistsException(String message) {
        super(message);
    }

    public OptionAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public OptionAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
