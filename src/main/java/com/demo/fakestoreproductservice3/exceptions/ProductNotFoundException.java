package com.demo.fakestoreproductservice3.exceptions;

public class ProductNotFoundException extends Exception {
    // CTOR
    public ProductNotFoundException(String message) {
        super(message);
    }
}
