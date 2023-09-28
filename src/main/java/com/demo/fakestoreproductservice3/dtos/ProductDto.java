package com.demo.fakestoreproductservice3.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    // Fields
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private RatingDto rating;
}
