package com.demo.fakestoreproductservice3.services.proxy;

import com.demo.fakestoreproductservice3.client.fakestoreapi.FakeStoreProductClient;
import com.demo.fakestoreproductservice3.client.fakestoreapi.domain.FakeStoreProductDto;
import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.models.Rating;
import com.demo.fakestoreproductservice3.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceProxyImp implements ProductService {
    // Fields
    private FakeStoreProductClient fakeStoreApiClient;

    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtoList = this.fakeStoreApiClient.getAllProducts();
        if (fakeStoreProductDtoList != null) {
            return fakeStoreProductDtoList.stream()
                    .map(fakeStoreProductDto -> this.mapToProduct(fakeStoreProductDto))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Optional<Product> getSingleProduct(Long id) {
        return this.fakeStoreApiClient.getSingleProduct(id)
                .map(fakeStoreProductDto -> this.mapToProduct(fakeStoreProductDto));
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.buildFakeStoreProductDto(product);
        fakeStoreProductDto = this.fakeStoreApiClient.addProduct(fakeStoreProductDto);
        return this.mapToProduct(fakeStoreProductDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.buildFakeStoreProductDto(product);
        fakeStoreProductDto = this.fakeStoreApiClient.replaceProduct(id, fakeStoreProductDto);
        return this.mapToProduct(fakeStoreProductDto);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = this.buildFakeStoreProductDto(product);
        fakeStoreProductDto = this.fakeStoreApiClient.updateProduct(id, fakeStoreProductDto);
        return this.mapToProduct(fakeStoreProductDto);
    }

    @Override
    public Optional<Product> removeProduct(Long id) {
        return this.fakeStoreApiClient.removeProduct(id)
                .map(fakeStoreProductDto -> this.mapToProduct(fakeStoreProductDto));
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
