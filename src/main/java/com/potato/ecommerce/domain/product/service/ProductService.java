package com.potato.ecommerce.domain.product.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.CATEGORY_NOT_FOUND;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PRODUCT_NOT_FOUND;
import static com.potato.ecommerce.global.exception.ExceptionMessage.STORE_NOT_FOUND;

import com.potato.ecommerce.domain.category.entity.ProductCategoryEntity;
import com.potato.ecommerce.domain.category.repository.ProductCategoryRepository;
import com.potato.ecommerce.domain.product.dto.ProductDetailResponse;
import com.potato.ecommerce.domain.product.dto.ProductRequest;
import com.potato.ecommerce.domain.product.dto.ProductResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.product.dto.ShopProductResponse;
import com.potato.ecommerce.domain.product.entity.ProductEntity;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
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
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public ProductResponse createProduct(String businessNumber, ProductRequest requestDto) {

        StoreEntity storeEntity = storeRepository.findByBusinessNumber(businessNumber)
            .orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND.toString()));

        ProductCategoryEntity productCategory = productCategoryRepository.findById(requestDto.getProductCategoryId())
            .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND.toString()));

        ProductEntity productEntity = ProductEntity.builder()
            .store(storeEntity)
            .productCategory(productCategory)
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .price(requestDto.getPrice())
            .stock(requestDto.getStock())
            .build();

        productRepository.save(productEntity);

        return new ProductResponse(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getDescription(),
            productEntity.getPrice(),
            productEntity.getStock(),
            productEntity.getStore().getId(),
            productCategory.getId());
    }

    public RestPage<ProductSimpleResponse> findAllProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.findAll(pageRequest);

        List<ProductSimpleResponse> productSimpleResponses = productPage.getContent().stream()
            .map(ProductSimpleResponse::of)
            .toList();

        return new RestPage<>(productSimpleResponses, page, size, productPage.getTotalElements());
    }

    public ProductDetailResponse findProductDetail(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND.toString()));

        ProductCategoryEntity productCategory = productEntity.getProductCategory();
        String oneDepthName = productCategory.getOneDepth().getOneDepth();
        String twoDepthName = productCategory.getTwoDepth().getTwoDepth();
        String threeDepthName = productCategory.getThreeDepth().getThreeDepth();

        return new ProductDetailResponse(
            oneDepthName,
            twoDepthName,
            threeDepthName,
            productEntity.getName(),
            productEntity.getDescription(),
            productEntity.getPrice().toString()
        );
    }

    public RestPage<ShopProductResponse> findProductsByShopId(Long shopId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productsPage = productRepository.findByStoreId(shopId, pageRequest);

        Page<ShopProductResponse> shopProductResponsesPage = productsPage.map(ShopProductResponse::of);


        return new RestPage<>(shopProductResponsesPage);
    }

    public RestPage<ProductSimpleResponse> findProductsByCategoryId(Long productCategoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.findByProductCategory_Id(productCategoryId, pageRequest);

        Page<ProductSimpleResponse> productSimpleResponsesPage = productPage.map(ProductSimpleResponse::of);

        return new RestPage<>(productSimpleResponsesPage);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND.toString()));

        ProductCategoryEntity categoryEntity = productCategoryRepository.findById(updateRequest.getProductCategoryId())
            .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND.toString()));

        productEntity.updateFromRequest(updateRequest);
        productEntity.updateProductCategory(categoryEntity);

        productRepository.save(productEntity);

        return ProductResponse.builder()
            .id(productEntity.getId())
            .name(productEntity.getName())
            .description(productEntity.getDescription())
            .price(productEntity.getPrice())
            .stock(productEntity.getStock())
            .storeId(productEntity.getStore().getId())
            .productCategoryId(productEntity.getProductCategory().getId())
            .build();
    }

    @Transactional
    public void softDeleteProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND.toString()));

        productEntity.softDelete();
        productRepository.delete(productEntity);
    }
}
