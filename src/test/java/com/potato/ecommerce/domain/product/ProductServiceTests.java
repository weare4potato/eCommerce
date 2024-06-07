package com.potato.ecommerce.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.potato.ecommerce.domain.category.entity.ProductCategoryEntity;
import com.potato.ecommerce.domain.category.repository.ProductCategoryRepository;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductJdbcRepository;
import com.potato.ecommerce.domain.product.repository.ProductQueryRepositoryImpl;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.domain.product.service.ProductService;
import com.potato.ecommerce.domain.s3.service.ImageService;
import com.potato.ecommerce.domain.store.StoreSteps;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductQueryRepositoryImpl productQueryRepository;

    @Mock
    private ProductJdbcRepository jdbcRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void Product_create() {
        // Arrange
        String businessNumber = "1111111111";
        String url = "http://image.url";
        final ProductRequest productRequest = ProductSteps.createProductRequest();
        final StoreEntity store = StoreSteps.createStore(passwordEncoder);
        final ProductCategoryEntity productCategory = new ProductCategoryEntity();
        final ProductEntity product = ProductSteps.createProductWithStoreAndCategory(productRequest, store,
            productCategory);

        given(storeRepository.findByBusinessNumber(businessNumber)).willReturn(Optional.of(store));
        given(productCategoryRepository.findById(anyLong())).willReturn(
            Optional.of(productCategory));
        given(productRepository.save(any(ProductEntity.class))).willReturn(product);

        // Act
        final ProductResponse response = productService.createProduct(businessNumber,
            productRequest,
            url);

        // Assert
        assertThat(response.getName()).isEqualTo(productRequest.getName());
        verify(imageService, times(1)).save(product, url);
    }

    @Test
    void Product_update() {
        // Arrange
        Long productId = 1L;
        Long categoryId = 1L;
        final ProductUpdateRequest productUpdate = ProductSteps.createProductUpdate(productId, categoryId);

        final ProductEntity product = ProductSteps.createProduct();
        final ProductCategoryEntity productCategory = new ProductCategoryEntity();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(productCategoryRepository.findById(categoryId)).willReturn(
            Optional.of(productCategory));
        given(productRepository.save(any(ProductEntity.class))).willReturn(product);

        // Act
        final ProductResponse response = productService.updateProduct(productId,
            productUpdate);

        // Assert
        assertThat(response.getPrice()).isEqualTo(productUpdate.getPrice());
        assertThat(response.getStock()).isEqualTo(productUpdate.getStock());
    }

    @Test
    void Product_delete() {
        // Arrange
        Long productId = 1L;
        final ProductEntity product = ProductSteps.createProduct();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // Act
        productService.softDeleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).delete(product);
        assertThat(product.getIsDeleted()).isTrue();
    }

}
