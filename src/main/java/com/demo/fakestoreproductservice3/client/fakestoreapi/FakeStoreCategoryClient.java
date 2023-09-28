package com.demo.fakestoreproductservice3.client.fakestoreapi;

import com.demo.fakestoreproductservice3.client.fakestoreapi.domain.FakeStoreProductDto;
import com.demo.fakestoreproductservice3.config.Config;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class FakeStoreCategoryClient {
    // Fields
    private RestTemplate restTemplate;

    // Behaviors
    public List<String> getAllCategories() {
        ResponseEntity<String[]> response = this.restTemplate.getForEntity(Config.FAKE_STORE_CATEGORIES, String[].class);
        if (response.hasBody())
            return Arrays.asList(response.getBody());
        return null;
    }

    public List<FakeStoreProductDto> getProductsByCategory(String categoryName) {
        ResponseEntity<FakeStoreProductDto[]> response = this.restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS_BY_CATEGORY, FakeStoreProductDto[].class, categoryName);
        if (response.hasBody())
            return Arrays.asList(response.getBody());
        return null;
    }
}
