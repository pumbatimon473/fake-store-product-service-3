package com.demo.fakestoreproductservice3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Error Handing: 3rd Method
 * Setting ResponseStatus for the Exception class
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND, reason = "No products are available for the mentioned category!")
public class NoProductsException extends Exception {
    // CTOR
    public NoProductsException(String message) {
        super(message);
    }
}
