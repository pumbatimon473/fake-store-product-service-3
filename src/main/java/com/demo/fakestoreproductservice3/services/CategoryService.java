package com.demo.fakestoreproductservice3.services;

import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;

import java.util.Collection;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Product> getProductsByCategory(String categoryName);
}
