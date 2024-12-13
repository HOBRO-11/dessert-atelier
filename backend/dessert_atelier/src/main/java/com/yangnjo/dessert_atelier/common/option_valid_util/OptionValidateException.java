package com.yangnjo.dessert_atelier.common.option_valid_util;

public class OptionValidateException extends RuntimeException {

    public OptionValidateException() {
        super();
    }

    public OptionValidateException(String message) {
        super(message);
    }

    public OptionValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptionValidateException(Throwable cause) {
        super(cause);
    }
}
