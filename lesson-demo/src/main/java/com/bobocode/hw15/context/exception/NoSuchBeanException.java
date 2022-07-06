package com.bobocode.hw15.context.exception;

public class NoSuchBeanException extends RuntimeException {

    public NoSuchBeanException() {
        super();
    }

    public NoSuchBeanException(String message) {
        super(message);
    }

    public NoSuchBeanException(String message, Throwable cause) {
        super(message, cause);
    }
}
