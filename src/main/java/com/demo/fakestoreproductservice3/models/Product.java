package com.demo.fakestoreproductservice3.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    // Fields
    private String title;
    private Double price;
    private String description;
    @ManyToOne
    private Category category;
    private String image;
    @OneToOne(mappedBy = "product")
    private Rating rating;
}
