package com.potato.ecommerce.domain.store.entity;

import com.potato.ecommerce.domain.store.model.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride.Wheres;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE stores SET is_deleted = true WHERE store_id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "stores")
/*todo 삭제된 entity 또한 추가하려면 @filter, @filterDef 사용*/
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String businessNumber;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    private boolean isDeleted = Boolean.FALSE;

    @Builder
    private StoreEntity(Long id, String email, String password, String name, String description,
        String phone,
        String businessNumber, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.businessNumber = businessNumber;
        this.createdAt = createdAt;
    }

    public static StoreEntity fromModel(Store store) {
        return StoreEntity.builder()
            .id(store.getId())
            .email(store.getEmail())
            .password(store.getPassword())
            .name(store.getName())
            .description(store.getDescription())
            .phone(store.getPhone())
            .businessNumber(store.getBusinessNumber())
            .createdAt(store.getCreatedAt())
            .build();
    }


    public Store toModel() {
        return Store.builder()
            .id(id)
            .email(email)
            .password(password)
            .name(name)
            .description(description)
            .phone(phone)
            .businessNumber(businessNumber)
            .createdAt(createdAt)
            .build();
    }
}
