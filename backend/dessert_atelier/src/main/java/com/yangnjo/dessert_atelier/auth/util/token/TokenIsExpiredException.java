package com.yangnjo.dessert_atelier.auth.util.token;

public class TokenIsExpiredException extends RuntimeException{

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException() {
        super();
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

}
