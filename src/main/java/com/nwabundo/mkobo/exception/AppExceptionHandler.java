package com.nwabundo.mkobo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = com.chompfooddeliveryapp.exception.BadRequestException.class)
    public ResponseEntity<Object> apiRequestHandler(com.chompfooddeliveryapp.exception.BadRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
       com.chompfooddeliveryapp.exception.ExceptionPayLoad exception =  new com.chompfooddeliveryapp.exception.ExceptionPayLoad(e.getMessage(),
                e,
               badRequest,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, badRequest);
    }


}
