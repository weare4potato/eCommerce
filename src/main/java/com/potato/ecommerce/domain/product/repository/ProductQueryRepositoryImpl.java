package com.potato.ecommerce.domain.product.repository;

import static com.potato.ecommerce.domain.product.entity.QProductEntity.productEntity;
import static com.potato.ecommerce.domain.s3.entity.QImageEntity.imageEntity;
import static com.querydsl.core.types.Projections.fields;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.product.dto.ProductSimpleResponse;
import com.potato.ecommerce.domain.product.dto.ShopProductResponse;
import com.potato.ecommerce.domain.product.entity.QProductEntity;
import com.potato.ecommerce.global.util.RestPage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public RestPage<ProductListResponse> getProducts(String subject, int page, int size) {
        QProductEntity product = productEntity;

        Pageable pageable = PageRequest.of(page, size);

        List<ProductListResponse> products =
            queryFactory.select(fields(ProductListResponse.class,
                    product.id.as("productId"),
                    product.name,
                    product.productCategory.id.as("productCategoryId"),
                    product.price,
                    product.stock))
                .from(product)
                .where(product.store.businessNumber.eq(subject))
                .orderBy(product.createdAt.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new RestPage<>(new PageImpl<>(products, pageable, products.size()));
    }

    @Override
    public RestPage<ProductSimpleResponse> getSimpleProducts(int page, int size) {
        List<ProductSimpleResponse> productSimpleResponses = queryFactory.select(
                fields(ProductSimpleResponse.class,
                    productEntity.id,
                    productEntity.name,
                    productEntity.price,
                    imageEntity.url
                    ))
            .from(productEntity)
            .leftJoin(imageEntity).on(imageEntity.productEntity.id.eq(productEntity.id))
            .orderBy(productEntity.createdAt.asc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        Long total = queryFactory.select(productEntity.id.count()).from(productEntity).fetchOne();
        Pageable pageable = PageRequest.of(page, size);
        return new RestPage<>(new PageImpl<>(productSimpleResponses, pageable, total));
    }

    @Override
    public RestPage<ShopProductResponse> findByStoreId(Long shopId, int page, int size) {
        List<Long> ids = queryFactory.select(productEntity.id).from(productEntity)
            .where(productEntity.store.id.eq(shopId)
                .and(productEntity.isDeleted.eq(false)))
            .orderBy(productEntity.id.asc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        List<ShopProductResponse> shopProductResponses = queryFactory.select(
                fields(ShopProductResponse.class,
                    productEntity.id,
                    productEntity.name,
                    productEntity.price
                ))
            .from(productEntity)
            .where(productEntity.id.in(ids))
            .fetch();

        Long total = queryFactory.select(productEntity.id.count()).from(productEntity)
            .where(productEntity.store.id.eq(shopId)).fetchOne();

        Pageable pageable = PageRequest.of(page, size);
        return new RestPage<>(new PageImpl<>(shopProductResponses, pageable, total));
    }

    @Override
    public RestPage<ProductSimpleResponse> findAllByProductName(String productName, int page,
        int size) {
        List<Long> ids = queryFactory.select(productEntity.id).from(productEntity)
            .where(productEntity.name.stringValue().contains(productName)
                .and(productEntity.isDeleted.eq(false)))
            .orderBy(productEntity.id.asc())
            .offset((long) page * size)
            .limit(size)
            .fetch();

        List<ProductSimpleResponse> shopProductResponses = queryFactory.select(
                fields(ProductSimpleResponse.class,
                    productEntity.id,
                    productEntity.name,
                    productEntity.price
                ))
            .from(productEntity)
            .where(productEntity.id.in(ids))
            .fetch();

        Long total = queryFactory.select(productEntity.id.count()).from(productEntity)
            .where(productEntity.name.stringValue().contains(productName)).fetchOne();

        Pageable pageable = PageRequest.of(page, size);
        return new RestPage<>(new PageImpl<>(shopProductResponses, pageable, total));
    }

    @Override
    public RestPage<ProductSimpleResponse> findProductsByCategory(Long categoryId, int page,
        int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductSimpleResponse> productSimpleResponses = queryFactory.select(
                Projections.fields(
                    ProductSimpleResponse.class,
                    productEntity.id,
                    productEntity.name,
                    productEntity.price))
            .from(productEntity)
            .where(productEntity.productCategory.id.eq(categoryId))
            .orderBy(productEntity.id.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory.
            select(productEntity.id.count())
            .from(productEntity)
            .where(productEntity.productCategory.id.eq(categoryId))
            .fetchOne();

        return new RestPage<>(new PageImpl<>(productSimpleResponses, pageable, total));
    }
}
