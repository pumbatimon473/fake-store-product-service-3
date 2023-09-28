package com.demo.fakestoreproductservice3.exceptions;

import lombok.Getter;

@Getter
public class ClientErrorException extends Exception {
    // Fields
    private Integer errorCode;

    // CTOR
    public ClientErrorException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
