package com.potato.ecommerce.domain.store.entity;

import static com.potato.ecommerce.global.exception.ExceptionMessage.BUSINESS_NUMBER_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.EMAIL_NOT_MATCH;
import static com.potato.ecommerce.global.exception.ExceptionMessage.PASSWORD_NOT_MATCH;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.ValidationException;
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

    public void passwordMatches(String password, PasswordEncoder passwordEncoder) {
        if(!passwordEncoder.matches(password, this.password)){
            throw new ValidationException(PASSWORD_NOT_MATCH.toString());
        }
    }

    public void emailMatches(String email) {
        if(!this.email.equals(email)){
            throw new ValidationException(EMAIL_NOT_MATCH.toString());
        }
    }

    public void businessNumberMatches(String businessNumber) {
        if(!this.businessNumber.equals(businessNumber)){
            throw new ValidationException(BUSINESS_NUMBER_NOT_MATCH.toString());
        }
    }
}
