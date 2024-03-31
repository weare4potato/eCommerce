package com.potato.ecommerce.domain.product.entity;


import com.potato.ecommerce.domain.category.entity.CategoryEntity;
import com.potato.ecommerce.domain.store.entity.StoreEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StoreEntity store;

    @OneToOne(fetch = FetchType.LAZY)
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
    private Boolean isDelete;

    @Column(name = "creat_at")
    private LocalDateTime createdAt;

    @Builder
    private ProductEntity(StoreEntity store, CategoryEntity category, String name,
        String description,
        Integer price, Integer stock, Boolean isDelete, LocalDateTime createdAt) {
        this.store = store;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.isDelete = isDelete;
        this.createdAt = createdAt;
    }
}
