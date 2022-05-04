package com.olix.stock.domain.exception;

public class ModelNotFoundException extends BusinessException {

    public ModelNotFoundException(String message) {
        super(message);
    }
}
