package com.design.order.exception;

public class DuplicateOrderNoException extends RuntimeException {
    public DuplicateOrderNoException(String message) {
        super(message);
    }
}
