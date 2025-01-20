package com.yangnjo.dessert_atelier.auth.exception;

public class RefreshTokenAlreadyExistsException extends RuntimeException {

    public RefreshTokenAlreadyExistsException() {
        super();
    }

    public RefreshTokenAlreadyExistsException(String message) {
        super(message);
    }

    public RefreshTokenAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
