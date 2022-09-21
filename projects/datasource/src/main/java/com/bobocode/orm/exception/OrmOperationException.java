package com.bobocode.orm.exception;

public class OrmOperationException extends RuntimeException {

    public OrmOperationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
