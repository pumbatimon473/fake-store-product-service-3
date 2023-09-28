package com.demo.fakestoreproductservice3.config;

import com.demo.fakestoreproductservice3.FakeStoreProductService3Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
public class Config {
    // Fake Store API Endpoints
    public static final String FAKE_STORE_API_HOST = "https://fakestoreapi.com";
    public static final String FAKE_STORE_PRODUCTS = FAKE_STORE_API_HOST + "/products";
    public static final String FAKE_STORE_PRODUCTS_RESOURCE = FAKE_STORE_API_HOST + "/products/{id}";
    public static final String FAKE_STORE_CATEGORIES = FAKE_STORE_API_HOST + "/products/categories";
    public static final String FAKE_STORE_PRODUCTS_BY_CATEGORY = FAKE_STORE_API_HOST + "/products/category/{categoryName}";

    // Beans
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplate restTemplateHttpClient(RestTemplateBuilder builder) {
        return builder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
    }

    @Bean
    public Logger log() {
        return LoggerFactory.getLogger(FakeStoreProductService3Application.class);
    }
}
