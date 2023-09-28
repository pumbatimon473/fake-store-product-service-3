package com.demo.fakestoreproductservice3.controllers;

import com.demo.fakestoreproductservice3.dtos.ErrorDto;
import com.demo.fakestoreproductservice3.dtos.ProductDto;
import com.demo.fakestoreproductservice3.dtos.RatingDto;
import com.demo.fakestoreproductservice3.exceptions.ClientErrorException;
import com.demo.fakestoreproductservice3.exceptions.DeleteErrorException;
import com.demo.fakestoreproductservice3.exceptions.ProductNotFoundException;
import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.services.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    // Fields
    private ProductService productService;
    private Logger log;

    // Behaviors
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() throws ClientErrorException {
        ResponseEntity<List<ProductDto>> response;
        try {
            response = new ResponseEntity<>(this.productService.getAllProducts().stream()
                    .map(product -> this.mapToProductDto(product))
                    .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "getAllProducts");
            throw new ClientErrorException(101, "Client Error: getAllProducts");
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable(name = "id") Long id) throws ProductNotFoundException, ClientErrorException {
        ResponseEntity<ProductDto> response;
        try {
            Optional<ProductDto> productDtoOptional = this.productService.getSingleProduct(id)
                    .map(product -> this.mapToProductDto(product));
            if (productDtoOptional.isEmpty()) throw new ProductNotFoundException("Product with id " + id + " does not exist!");
            response = new ResponseEntity<>(productDtoOptional.get(), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            this.logError(e, "getSingleProduct");
            throw new ClientErrorException(102, "Client Error: getSingleProduct");
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) throws ClientErrorException {
        ResponseEntity<ProductDto> response;
        try {
            Product product = this.buildProduct(productDto);
            response = new ResponseEntity<>(this.mapToProductDto(this.productService.addProduct(product)), HttpStatus.CREATED);
        } catch (Exception e) {
            this.logError(e, "addProduct");
            throw new ClientErrorException(103, "Client Error: addProduct");
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto productDto) throws ClientErrorException {
        ResponseEntity<ProductDto> response;
        try {
            Product product = this.buildProduct(productDto);
            response = new ResponseEntity<>(this.mapToProductDto(this.productService.replaceProduct(id, product)), HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "replaceProduct");
            throw new ClientErrorException(104, "Client Error: replaceProduct");
        }
        return response;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long id, @RequestBody ProductDto productDto) throws ClientErrorException {
        ResponseEntity<ProductDto> response;
        try {
            Product product = this.buildProduct(productDto);
            response = new ResponseEntity<>(this.mapToProductDto(this.productService.updateProduct(id, product)), HttpStatus.OK);
        } catch (Exception e) {
            this.logError(e, "updateProduct");
            throw new ClientErrorException(105, "Client Error: updateProduct");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable(name = "id") Long id) throws DeleteErrorException, ClientErrorException {
        ResponseEntity<ProductDto> response;
        try {
            Optional<ProductDto> productDtoOptional = this.productService.removeProduct(id)
                    .map(product -> this.mapToProductDto(product));
            if (productDtoOptional.isEmpty()) throw new DeleteErrorException("Product with id " + id + " does not exist.");
            response = new ResponseEntity<>(productDtoOptional.get(), HttpStatus.OK);
        } catch (DeleteErrorException e) {
            throw e;
        } catch (Exception e) {
            this.logError(e, "removeProduct");
            throw new ClientErrorException(106, "Client Error: removeProduct");
        }
        return response;
    }

    /** Error Handling: 1st Method
     * Exception Handler at the controller level
     */
    @ExceptionHandler(DeleteErrorException.class)
    private ResponseEntity<ErrorDto> handleDeleteErrorException(Exception e) {
        return new ResponseEntity<>(new ErrorDto(null, e.getMessage()), HttpStatus.NOT_FOUND);
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
