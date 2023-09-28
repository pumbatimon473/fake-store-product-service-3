package com.demo.fakestoreproductservice3.services.proxy;

import com.demo.fakestoreproductservice3.client.fakestoreapi.FakeStoreCategoryClient;
import com.demo.fakestoreproductservice3.client.fakestoreapi.domain.FakeStoreProductDto;
import com.demo.fakestoreproductservice3.models.Category;
import com.demo.fakestoreproductservice3.models.Product;
import com.demo.fakestoreproductservice3.models.Rating;
import com.demo.fakestoreproductservice3.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceProxyImp implements CategoryService {
    // Fields
    private FakeStoreCategoryClient fakeStoreApiClient;

    // Behaviors
    @Override
    public List<Category> getAllCategories() {
        List<String> categoriesList = this.fakeStoreApiClient.getAllCategories();
        if (categoriesList != null)
            return categoriesList.stream()
                    .map(category -> new Category(category))
                    .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        List<FakeStoreProductDto> fakeStoreProductDtoList = this.fakeStoreApiClient.getProductsByCategory(categoryName);
        if (fakeStoreProductDtoList != null)
            return fakeStoreProductDtoList.stream()
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
