package com.potato.ecommerce.domain.member.entity;

import com.potato.ecommerce.domain.member.dto.UpdateMemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE members SET is_deleted = true WHERE member_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "members")
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

    private Long kakaoId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    private Boolean isDeleted = false;

    @Builder
    public MemberEntity(String email, String userName, String password, String phone,
        LocalDateTime createdAt, UserRoleEnum role, boolean authStatus, Long kakaoId) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.createdAt = createdAt;
        this.role = role;
        this.authStatus = authStatus;
        this.kakaoId = kakaoId;
    }

    private boolean authStatus;

    public void confirm() {
        this.authStatus = true;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean isNotMatchPassword(PasswordEncoder encoder, String password) {
        return !encoder.matches(password, this.password);
    }

    public boolean isNotMatchMember(Long id) {
        return !this.id.equals(id);
    }

    public boolean isNotAuthCheck() {
        return !this.authStatus;
    }

    public void update(UpdateMemberDto dto) {
        this.userName = dto.getUsername();
        this.phone = dto.getPhone();
    }

    public MemberEntity kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
