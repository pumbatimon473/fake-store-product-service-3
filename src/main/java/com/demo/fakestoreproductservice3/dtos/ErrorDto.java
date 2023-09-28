package com.demo.fakestoreproductservice3.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorDto {
    // Fields
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private Integer errorCode;
    private String errorMessage;
}
