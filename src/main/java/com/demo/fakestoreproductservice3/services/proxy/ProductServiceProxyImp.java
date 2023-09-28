package com.demo.fakestoreproductservice3.services.proxy;

import com.demo.fakestoreproductservice3.config.Config;
import com.demo.fakestoreproductservice3.domain.FakeStoreProductDto;
import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.models.Rating;
import com.demo.fakestoreproductservice3.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceProxyImp implements ProductService {
    // Fields
    private RestTemplate restTemplate;
    private RestTemplate restTemplateHttpClient;

    @Override
    public List<Product> getAllProducts() {
        ResponseEntity<FakeStoreProductDto[]> response = this.restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS, FakeStoreProductDto[].class);
        if (response.hasBody()) {
            return Arrays.stream(response.getBody())
                    .map(fakeStoreProductDto -> this.mapToProduct(fakeStoreProductDto))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Optional<Product> getSingleProduct(Long id) {
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.getForEntity(Config.FAKE_STORE_PRODUCTS_RESOURCE, FakeStoreProductDto.class, id);
        return Optional.ofNullable(this.mapToProduct(response.getBody()));
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.buildFakeStoreProductDto(product);
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.postForEntity(Config.FAKE_STORE_PRODUCTS, fakeStoreProductDto, FakeStoreProductDto.class);
        if (response.hasBody())
            return this.mapToProduct(response.getBody());
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.buildFakeStoreProductDto(product);
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fakeStoreProductDto);
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.exchange(Config.FAKE_STORE_PRODUCTS_RESOURCE, HttpMethod.PUT, requestEntity, FakeStoreProductDto.class, id);
        if (response.hasBody())
            return this.mapToProduct(response.getBody());
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.buildFakeStoreProductDto(product);
        fakeStoreProductDto = this.restTemplateHttpClient.patchForObject(Config.FAKE_STORE_PRODUCTS_RESOURCE, fakeStoreProductDto, FakeStoreProductDto.class, id);
        return this.mapToProduct(fakeStoreProductDto);
    }

    @Override
    public Optional<Product> removeProduct(Long id) {
        ResponseEntity<FakeStoreProductDto> response = this.restTemplate.exchange(Config.FAKE_STORE_PRODUCTS_RESOURCE, HttpMethod.DELETE, null, FakeStoreProductDto.class, id);
        return Optional.ofNullable(this.mapToProduct(response.getBody()));
    }

    private FakeStoreProductDto buildFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setImage(product.getImage());
        return fakeStoreProductDto;
    }

    private Product mapToProduct(FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) return null;
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(new Category(fakeStoreProductDto.getCategory()));
        product.setImage(fakeStoreProductDto.getImage());
        if (fakeStoreProductDto.getRating() != null)
            product.setRating(new Rating(
                fakeStoreProductDto.getRating().getRate(),
                fakeStoreProductDto.getRating().getCount()
            ));
        return product;
    }
}
