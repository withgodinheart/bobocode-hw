package com.bobocode.hw15.yv.context.exception;

public class NoUniqueBeanException extends RuntimeException {

    public NoUniqueBeanException() {
        super();
    }

    public NoUniqueBeanException(String message) {
        super(message);
    }

    public NoUniqueBeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
