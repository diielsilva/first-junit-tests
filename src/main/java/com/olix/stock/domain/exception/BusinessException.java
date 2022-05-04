package com.olix.stock.domain.exception;

public abstract class BusinessException extends RuntimeException {
    protected final String message;

    protected BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
