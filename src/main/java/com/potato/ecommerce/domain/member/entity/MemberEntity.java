package com.potato.ecommerce.domain.member.entity;

import com.potato.ecommerce.domain.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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


    @Builder
    public MemberEntity(String email, String userName, String password, String phone) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }

    public static MemberEntity fromModel(Member member) {
        return MemberEntity.builder()
            .email(member.getEmail())
            .userName(member.getUserName())
            .password(member.getPassword())
            .phone(member.getPhone())
            .build();
    }

    public Member toModel() {
        return Member.builder()
            .id(this.id)
            .email(this.email)
            .userName(this.userName)
            .password(this.password)
            .phone(this.phone)
            .build();
    }


}
