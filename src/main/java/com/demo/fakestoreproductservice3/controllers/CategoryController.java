package com.demo.fakestoreproductservice3.controllers;

import com.demo.fakestoreproductservice3.dtos.ProductDto;
import com.demo.fakestoreproductservice3.dtos.RatingDto;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.services.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CategoryController {
    // Fields
    private CategoryService categoryService;
    private Logger log;

    // Behaviors
    @GetMapping("/products/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        ResponseEntity<List<String>> response;
        try {
            return new ResponseEntity<>(this.categoryService.getAllCategories().stream()
                    .map(category -> category.getName())
                    .collect(Collectors.toList()),
            HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "getAllCategories");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/products/category/{categoryName}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable(name = "categoryName") String categoryName) {
        ResponseEntity<List<ProductDto>> response;
        try {
            response = new ResponseEntity<>(this.categoryService.getProductsByCategory(categoryName).stream()
                    .map(product -> this.mapToProductDto(product))
                    .collect(Collectors.toList()),
            HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "getProductsByCategory");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().getName());
        productDto.setImage(product.getImage());
        if (product.getRating() != null)
            productDto.setRating(new RatingDto(product.getRating().getRate(), product.getRating().getCount()));
        return productDto;
    }

    private void logError(Exception e, String method) {
        this.log.info(":: ERROR :: Exception caught in " + method + "() :: " + e.getClass() + ": " + e.getMessage());
    }
}
