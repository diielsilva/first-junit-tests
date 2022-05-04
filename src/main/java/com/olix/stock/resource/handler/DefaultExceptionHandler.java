package com.olix.stock.resource.handler;

import com.olix.stock.domain.exception.BusinessException;
import com.olix.stock.resource.representation.standard.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseMessage> handleBusinessException(BusinessException exception) {
        ResponseMessage responseMessage = this.generateResponseMessage(exception.getMessage());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseMessage generateResponseMessage(String message) {
        return new ResponseMessage(message);
    }
}
