package com.demo.fakestoreproductservice3.services;

import com.demo.fakestoreproductservice3.models.Product;
import io.micrometer.observation.ObservationFilter;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getSingleProduct(Long id);

    Product addProduct(Product product);

    Product replaceProduct(Long id, Product product);

    Product updateProduct(Long id, Product product);

    Optional<Product> removeProduct(Long id);
}
