package com.yangnjo.dessert_atelier.domain_service.auth.exception;

public class RefreshTokenNotMatchedException extends RuntimeException {

    public RefreshTokenNotMatchedException() {
        super();
    }

    public RefreshTokenNotMatchedException(Throwable ex) {
        super(ex);
    }

    public RefreshTokenNotMatchedException(String message) {
        super(message);
    }

    public RefreshTokenNotMatchedException(String message, Throwable ex) {
        super(message, ex);
    }

}
