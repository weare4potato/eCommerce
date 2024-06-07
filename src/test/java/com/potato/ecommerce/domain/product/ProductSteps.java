package com.potato.ecommerce.domain.product;

import com.potato.ecommerce.domain.category.entity.ProductCategoryEntity;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.store.StoreSteps;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProductSteps {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static ProductEntity createProductWithStockAndPrice(int stock, Long price) {
        StoreEntity store = StoreSteps.createStore(passwordEncoder);
        ProductCategoryEntity productCategory = new ProductCategoryEntity();
        return ProductEntity.builder()
            .store(store)
            .productCategory(productCategory)
            .name("name")
            .description("description")
            .price(price)
            .stock(stock)
            .build();
    }

    static ProductUpdateRequest createProductUpdate(final Long productId,
        final Long categoryId) {
        String productName = "updateName";
        String description = "updateDescription";
        Long price = 2000L;
        int stock = 15;
        return new ProductUpdateRequest(categoryId,
            productName, description, price, stock);
    }

    static ProductRequest createProductRequest() {
        Long productCategoryId = 1L;
        String name = "name";
        String description = "description";
        Long price = 1000L;
        Integer stock = 10;
        return new ProductRequest(productCategoryId, name, description, price, stock, null);
    }

    static ProductEntity createProduct() {
        String name = "name";
        String description = "description";
        Long price = 1000L;
        int stock = 10;

        return ProductEntity.builder()
            .store(StoreSteps.createStore(passwordEncoder))
            .productCategory(new ProductCategoryEntity())
            .name(name)
            .description(description)
            .price(price)
            .stock(stock)
            .build();
    }

    static ProductEntity createProductWithStoreAndCategory(final ProductRequest productRequest,
        final StoreEntity store, final ProductCategoryEntity productCategory) {
        return ProductEntity.builder()
            .store(store)
            .productCategory(productCategory)
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .stock(productRequest.getStock())
            .build();
    }
}
