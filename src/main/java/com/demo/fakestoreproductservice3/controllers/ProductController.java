package com.demo.fakestoreproductservice3.controllers;

import com.demo.fakestoreproductservice3.dtos.ProductDto;
import com.demo.fakestoreproductservice3.dtos.RatingDto;
import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ProductController {
    // Fields
    private ProductService productService;
    private Logger log;

    // Behaviors
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        ResponseEntity<List<ProductDto>> response;
        try {
            response = new ResponseEntity<>(this.productService.getAllProducts().stream()
                    .map(product -> this.mapToProductDto(product))
                    .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "getAllProducts");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable(name = "id") Long id) {
        ResponseEntity<ProductDto> response;
        try {
            response = this.productService.getSingleProduct(id)
                    .map(product -> new ResponseEntity<>(this.mapToProductDto(product), HttpStatus.OK))
                    .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            this.logError(e, "getSingleProduct");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ResponseEntity<ProductDto> response;
        try {
            Product product = this.buildProduct(productDto);
            response = new ResponseEntity<>(this.mapToProductDto(this.productService.addProduct(product)), HttpStatus.CREATED);
        } catch (Exception e) {
            this.logError(e, "addProduct");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto productDto) {
        ResponseEntity<ProductDto> response;
        try {
            Product product = this.buildProduct(productDto);
            response = new ResponseEntity<>(this.mapToProductDto(this.productService.replaceProduct(id, product)), HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "replaceProduct");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto productDto) {
        ResponseEntity<ProductDto> response;
        try {
            Product product = this.buildProduct(productDto);
            response = new ResponseEntity<>(this.mapToProductDto(this.productService.updateProduct(id, product)), HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "updateProduct");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable(name = "id") Long id) {
        ResponseEntity<ProductDto> response;
        try {
            response = this.productService.removeProduct(id)
                    .map(product -> new ResponseEntity<>(this.mapToProductDto(product), HttpStatus.OK))
                    .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            this.logError(e, "removeProduct");
            response = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private Product buildProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(new Category(productDto.getCategory()));
        product.setImage(productDto.getImage());
        return product;
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
