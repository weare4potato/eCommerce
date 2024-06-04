package com.potato.ecommerce.domain.product;

import com.potato.ecommerce.domain.category.entity.ProductCategoryEntity;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.store.StoreSteps;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ProductSteps {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static ProductEntity createProductWithStoreAndCategory(int stock, Long price) {
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
}
