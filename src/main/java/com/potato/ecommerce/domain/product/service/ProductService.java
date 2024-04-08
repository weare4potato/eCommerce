package com.potato.ecommerce.domain.product.service;

import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.category.repository.CategoryRepository;
import com.potato.ecommerce.domain.product.dto.ProductDetailResponse;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.dto.ShopProductResponse;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.model.Product;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponse createProduct(String businessNumber, ProductRequest requestDto) {

        StoreEntity storeEntity = storeRepository.findBySubject(businessNumber);

        CategoryEntity category = categoryRepository.findById(requestDto.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        Product product = Product.builder()
            .store(storeEntity)
            .category(category)
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .price(requestDto.getPrice())
            .stock(requestDto.getStock())
            .isDeleted(false)
            .createdAt(LocalDateTime.now())
            .build();

        productRepository.save(ProductEntity.fromModel(product));

        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            product.getStore().getId(),
            product.getCategory().getId());
    }

    @Transactional
    public RestPage<ProductSimpleResponse> findAllProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.findAll(pageRequest);

        List<ProductSimpleResponse> productSimpleResponses = productPage.getContent().stream()
            .map(entity -> new ProductSimpleResponse(entity.getName(), entity.getPrice()))
            .collect(Collectors.toList());

        return new RestPage<>(productSimpleResponses, page, size, productPage.getTotalElements());
    }

    @Transactional
    public ProductDetailResponse findProductDetail(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        CategoryEntity category = productEntity.getCategory();
        String oneDepthName = category.getOneDepth().toString();
        String twoDepthName = category.getTwoDepth().toString();
        String threeDepthName = category.getThreeDepth().toString();

        return new ProductDetailResponse(
            oneDepthName,
            twoDepthName,
            threeDepthName,
            productEntity.getName(),
            productEntity.getDescription(),
            productEntity.getPrice().toString()
        );
    }

    @Transactional
    public RestPage<ShopProductResponse> findProductsByShopId(Long shopId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productsPage = productRepository.findByStoreId(shopId, pageRequest);

        Page<ShopProductResponse> shopProductResponsesPage = productsPage.map(product -> new ShopProductResponse(
            product.getName(),
            product.getPrice()));

        return new RestPage<>(shopProductResponsesPage);
    }

    @Transactional
    public RestPage<ProductSimpleResponse> findProductsByCategoryId(Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.findByCategoryId(categoryId, pageRequest);

        Page<ProductSimpleResponse> productSimpleResponsesPage = productPage.map(entity -> new ProductSimpleResponse(
            entity.getName(),
            entity.getPrice()));

        return new RestPage<>(productSimpleResponsesPage);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        CategoryEntity categoryEntity = categoryRepository.findById(updateRequest.getCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));

        Product product = Product.fromEntity(productEntity);

        product.updateFromRequest(updateRequest);
        product.updateCategory(categoryEntity);

        ProductEntity updatedProductEntity = product.toEntity();
        productRepository.save(updatedProductEntity);

        return new ProductResponse(product);
    }

    @Transactional
    public void softDeleteProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다."));

        Product product = Product.fromEntity(productEntity);
        productRepository.delete(product.toEntity());
    }
}
