package com.potato.ecommerce.domain.product.entity;


import com.potato.ecommerce.domain.category.entity.ProductCategoryEntity;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.global.exception.ExceptionMessage;
import com.potato.ecommerce.global.exception.custom.AuthenticationFailedException;
import com.potato.ecommerce.global.exception.custom.OutOfStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@EntityListeners(AuthenticationFailedException.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE product_id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "products", indexes = {
    @Index(name = "idx_is_deleted_and_product_category_id", columnList = "is_deleted, product_category_id"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StoreEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private ProductCategoryEntity productCategory;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private ProductEntity(StoreEntity store, ProductCategoryEntity productCategory, String name,
        String description,
        Long price, Integer stock) {
        this.store = store;
        this.productCategory = productCategory;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public void removeStock(Integer stock) {
        Integer restStock = this.stock - stock;
        if (restStock < 0) {
            throw new OutOfStockException(
                ExceptionMessage.PRODUCT_OUT_OF_STOCK.toString() + this.stock);
        }
        this.stock = restStock;
    }

    public void addStock(Integer stock) {
        this.stock += stock;
    }

    public Long getTotalPrice(Integer quantity) {
        return price * quantity;
    }

    public void updateFromRequest(ProductUpdateRequest updateRequest) {
        this.name = updateRequest.getProductName();
        this.description = updateRequest.getDescription();
        this.price = updateRequest.getPrice();
        this.stock = updateRequest.getStock();
    }

    public void updateProductCategory(ProductCategoryEntity newProductCategory) {
        this.productCategory = newProductCategory;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
