package com.demo.fakestoreproductservice3.controllers;

import com.demo.fakestoreproductservice3.dtos.ErrorDto;
import com.demo.fakestoreproductservice3.exceptions.ClientErrorException;
import com.demo.fakestoreproductservice3.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** Error Handling: 2nd Method
 * Controller Advice - Provides a way to reduce redundant codes
 */

@ControllerAdvice
public class ExceptionAdvices {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception e) {
        return new ResponseEntity<>(new ErrorDto(404, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorDto> handleClientErrorException(ClientErrorException e) {
        return new ResponseEntity<>(new ErrorDto(e.getErrorCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
