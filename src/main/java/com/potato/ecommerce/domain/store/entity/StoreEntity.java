package com.potato.ecommerce.domain.store.entity;

import com.potato.ecommerce.domain.store.model.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
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
    private String licenseNumber;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    public static StoreEntity fromModel(Store store){
        return StoreEntity.builder()
            .email(store.getEmail())
            .password(store.getPassword())
            .name(store.getName())
            .description(store.getDescription())
            .phone(store.getPhone())
            .licenseNumber(store.getLicenseNumber())
            .createAt(store.getCreateAt())
            .build();
    }


    public Store toModel(){
        return Store.builder()
            .id(this.id)
            .email(this.email)
            .password(this.password)
            .name(this.name)
            .description(this.description)
            .phone(this.phone)
            .licenseNumber(this.licenseNumber)
            .createAt(this.createAt)
            .build();
    }
}
