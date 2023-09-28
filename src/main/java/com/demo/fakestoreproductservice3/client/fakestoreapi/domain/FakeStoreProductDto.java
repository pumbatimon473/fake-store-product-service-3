package com.demo.fakestoreproductservice3.client.fakestoreapi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    // Fields
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private FakeStoreRatingDto rating;
}
