package com.potato.ecommerce.domain.product.repository;

import static com.potato.ecommerce.domain.product.entity.QProductEntity.productEntity;
import static com.querydsl.core.types.Projections.fields;

import com.potato.ecommerce.domain.product.dto.ProductListResponse;
import com.potato.ecommerce.domain.product.entity.QProductEntity;
import com.potato.ecommerce.global.util.RestPage;
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

        List<ProductListResponse> products =
            queryFactory.select(fields(ProductListResponse.class,
                    product.name,
                    product.productCategory.id.as("productCategoryId"),
                    product.price,
                    product.stock))
                .from(product)
                .where(product.store.businessNumber.eq(subject))
                .orderBy(product.createdAt.asc())
                .offset(page)
                .limit(size)
                .fetch();

        Pageable pageable = PageRequest.of(page, size);
        return new RestPage<>(new PageImpl<>(products, pageable, products.size()));
    }
}
