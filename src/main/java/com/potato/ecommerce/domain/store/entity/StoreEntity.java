package com.potato.ecommerce.domain.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE stores SET is_deleted = true WHERE store_id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "stores", indexes = @Index(name = "idx_is_deleted_and_business_number", columnList = "is_deleted, business_number"))
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
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private final boolean isDeleted = false;

    @Builder
    private StoreEntity(Long id, String email, String password, String name, String description,
        String phone,
        String businessNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.businessNumber = businessNumber;
    }

    public void update(String name, String description, String phone) {
        this.name = name;
        this.description = description;
        this.phone = phone;
    }

    public boolean passwordMatches(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    public boolean emailMatches(String email) {
        return email.equals(this.email);
    }

    public boolean businessNumberMatches(String businessNumber) {
        return businessNumber.equals(this.businessNumber);
    }
}
