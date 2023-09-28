package com.demo.fakestoreproductservice3.services.proxy;

import com.demo.fakestoreproductservice3.config.Config;
import com.demo.fakestoreproductservice3.domain.FakeStoreProductDto;
import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.models.Rating;
import com.demo.fakestoreproductservice3.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceProxyImp implements CategoryService {
    // Fields
    private RestTemplate restTemplate;

    // Behaviors
    @Override
    public List<Category> getAllCategories() {
        ResponseEntity<String[]> response = this.restTemplate.getForEntity(Config.FAKE_STORE_CATEGORIES, String[].class);
        if (response.hasBody())
            return Arrays.stream(response.getBody())
                    .map(category -> new Category(category))
                    .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        ResponseEntity<FakeStoreProductDto[]> response = this.restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS_BY_CATEGORY, FakeStoreProductDto[].class, categoryName);
        if (response.hasBody())
            return Arrays.stream(response.getBody())
                    .map(fakeStoreProductDto -> this.mapToProduct(fakeStoreProductDto))
                    .collect(Collectors.toList());
        return null;
    }

    private Product mapToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(new Category(fakeStoreProductDto.getCategory()));
        product.setImage(fakeStoreProductDto.getImage());
        if (fakeStoreProductDto.getRating() != null)
            product.setRating(new Rating(fakeStoreProductDto.getRating().getRate(), fakeStoreProductDto.getRating().getCount()));
        return product;
    }
}
