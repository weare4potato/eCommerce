package com.potato.ecommerce.domain.product.entity;


import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.product.model.Product;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "store_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    @NotNull
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Builder
    private ProductEntity(Long id, StoreEntity store, CategoryEntity category, String name,
        String description,
        Integer price, Integer stock, Boolean isDeleted, LocalDateTime createdAt) {
        this.id = id;
        this.store = store;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public static ProductEntity fromModel(Product product) {
        return ProductEntity.builder()
            .store(product.getStore())
            .category(product.getCategory())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .stock(product.getStock())
            .isDeleted(product.getIsDeleted())
            .createdAt(product.getCreatedAt())
            .build();
    }

    public Product toModel() {
        return Product.builder()
            .id(this.id)
            .store(this.store)
            .category(this.category)
            .name(this.name)
            .description(this.description)
            .price(this.price)
            .stock(this.stock)
            .isDeleted(this.isDeleted)
            .build();
    }


}
