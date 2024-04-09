package com.potato.ecommerce.domain.product.entity;


import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.product.dto.ProductUpdateRequest;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import com.potato.ecommerce.global.exception.custom.OutOfStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE product_id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "products")
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
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    private ProductEntity(StoreEntity store, CategoryEntity category, String name,
        String description,
        Integer price, Integer stock, Boolean isDeleted, LocalDateTime createdAt) {
        this.store = store;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public void removeStock(Integer stock) {
        Integer restStock = this.stock - stock;
        if (restStock < 0) {
            throw new OutOfStockException("[ERROR] 상품의 재고가 부족합니다. 현재 재고 수량: " + this.stock);
        }
        this.stock = restStock;
    }

    public void addStock(Integer stock) {
        this.stock += stock;
    }

    public void updateFromRequest(ProductUpdateRequest updateRequest) {
        this.name = updateRequest.getProductName();
        this.description = updateRequest.getDescription();
        this.price = updateRequest.getPrice();
        this.stock = updateRequest.getStock();
    }

    public void updateCategory(CategoryEntity newCategory) {
        this.category = newCategory;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
