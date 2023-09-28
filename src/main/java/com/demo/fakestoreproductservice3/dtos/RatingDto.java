package com.demo.fakestoreproductservice3.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RatingDto {
    // Fields
    private Double rate;
    private Integer count;
}
