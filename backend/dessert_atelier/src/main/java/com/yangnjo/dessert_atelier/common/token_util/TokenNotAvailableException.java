package com.yangnjo.dessert_atelier.common.token_util;

public class TokenNotAvailableException extends RuntimeException {

    public TokenNotAvailableException(String message) {
        super(message);
    }

    public TokenNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNotAvailableException() {
        super();
    }

    public TokenNotAvailableException(Throwable cause) {
        super(cause);
    }

}
