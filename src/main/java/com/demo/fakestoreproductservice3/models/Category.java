package com.demo.fakestoreproductservice3.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category extends BaseModel {
    // Fields
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // CTOR
    public Category(String name) {
        this.name = name;
    }
}
