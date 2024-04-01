package com.potato.ecommerce.domain.member.entity;

import com.potato.ecommerce.domain.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "members")
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public static MemberEntity fromModel(Member member) {
        return MemberEntity.builder()
            .id(member.getId())
            .email(member.getEmail())
            .userName(member.getUserName())
            .password(member.getPassword())
            .phone(member.getPhone())
            .role(member.getRole())
            .build();
    }

    public Member toModel() {
        return Member.builder()
            .id(this.id)
            .email(this.email)
            .userName(this.userName)
            .password(this.password)
            .phone(this.phone)
            .role(this.role)
            .build();
    }


}
