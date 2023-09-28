package com.demo.fakestoreproductservice3.client.fakestoreapi;

import com.demo.fakestoreproductservice3.config.Config;
import com.demo.fakestoreproductservice3.client.fakestoreapi.domain.FakeStoreProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FakeStoreProductClient {
    // Fields
    private RestTemplate restTemplate;
    private RestTemplate restTemplateHttpClient;

    // Behaviors
    public List<FakeStoreProductDto> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response = this.restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS, FakeStoreProductDto[].class);
        if (response.hasBody()) {
            return Arrays.asList(response.getBody());
        }
        return null;
    }

    public Optional<FakeStoreProductDto> getSingleProduct(Long id) {
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS_RESOURCE, FakeStoreProductDto.class, id);
        return Optional.ofNullable(response.getBody());
    }

    public FakeStoreProductDto addProduct(FakeStoreProductDto fakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.postForEntity(Config.FAKE_STORE_PRODUCTS, fakeStoreProductDto, FakeStoreProductDto.class);
        if (response.hasBody())
            return response.getBody();
        return null;
    }

    public FakeStoreProductDto replaceProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fakeStoreProductDto);
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.exchange(Config.FAKE_STORE_PRODUCTS_RESOURCE, HttpMethod.PUT, requestEntity, FakeStoreProductDto.class, id);
        if (response.hasBody())
            return response.getBody();
        return null;
    }

    public FakeStoreProductDto updateProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        fakeStoreProductDto = this.restTemplateHttpClient.patchForObject(Config.FAKE_STORE_PRODUCTS_RESOURCE, fakeStoreProductDto, FakeStoreProductDto.class, id);
        return fakeStoreProductDto;
    }

    public Optional<FakeStoreProductDto> removeProduct(Long id) {
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.exchange(Config.FAKE_STORE_PRODUCTS_RESOURCE, HttpMethod.DELETE, null, FakeStoreProductDto.class, id);
        return Optional.ofNullable(response.getBody());
    }
}
