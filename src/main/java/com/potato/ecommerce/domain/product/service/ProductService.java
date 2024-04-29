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
import com.potato.ecommerce.domain.product.repository.ProductQueryRepositoryImpl;
import com.potato.ecommerce.domain.product.repository.ProductRepository;
import com.potato.ecommerce.domain.s3.service.ImageService;
import com.potato.ecommerce.domain.store.dto.StoreResponse;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.domain.store.repository.StoreRepository;
import com.potato.ecommerce.global.util.RestPage;
import jakarta.persistence.EntityNotFoundException;
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
    private final ProductQueryRepositoryImpl productQueryRepository;
    private final StoreRepository storeRepository;
    private final ProductCategoryRepository productCategoryRepository;

    private final ImageService imageService;

    @Transactional
    public ProductResponse createProduct(String businessNumber, ProductRequest requestDto,
        String url) {

        StoreEntity storeEntity = storeRepository.findByBusinessNumber(businessNumber)
            .orElseThrow(() -> new EntityNotFoundException(STORE_NOT_FOUND.toString()));

        ProductCategoryEntity productCategory = productCategoryRepository.findById(
                requestDto.getProductCategoryId())
            .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND.toString()));

        ProductEntity productEntity = ProductEntity.builder()
            .store(storeEntity)
            .productCategory(productCategory)
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .price(requestDto.getPrice())
            .stock(requestDto.getStock())
            .build();

        ProductEntity save = productRepository.save(productEntity);

        imageService.save(save, url);

        return new ProductResponse(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getDescription(),
            productEntity.getPrice(),
            productEntity.getStock(),
            new StoreResponse(productEntity.getStore()),
            productCategory.getId());
    }

    public RestPage<ProductSimpleResponse> findAllProducts(int page, int size) {
        return productQueryRepository.getSimpleProducts(page, size);
    }

    public ProductDetailResponse findProductDetail(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND.toString()));

        ProductCategoryEntity productCategory = productEntity.getProductCategory();
        String oneDepthName = productCategory.getOneDepth().getOneDepth();
        String twoDepthName = productCategory.getTwoDepth().getTwoDepth();
        String threeDepthName = productCategory.getThreeDepth().getThreeDepth();

        return new ProductDetailResponse(
            productEntity.getId(),
            oneDepthName,
            twoDepthName,
            threeDepthName,
            productEntity.getName(),
            productEntity.getDescription(),
            productEntity.getPrice(),
            new StoreResponse(productEntity.getStore())
        );
    }

    public RestPage<ShopProductResponse> findProductsByShopId(Long shopId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductEntity> productsPage = productRepository.findByStoreId(shopId, pageRequest);

        Page<ShopProductResponse> shopProductResponsesPage = productsPage.map(
            ShopProductResponse::of);

        return new RestPage<>(shopProductResponsesPage);
    }

    public RestPage<ProductSimpleResponse> findProductsByCategoryId2(Long categoryId, int page, int size) {
        return productQueryRepository.findProductsByCategory(categoryId, page, size);
    }



    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        ProductEntity productEntity = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND.toString()));

        ProductCategoryEntity categoryEntity = productCategoryRepository.findById(
                updateRequest.getProductCategoryId())
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
            .store(new StoreResponse(productEntity.getStore()))
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
