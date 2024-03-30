package com.potato.ecommerce.domain.member.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class Member {

    private Long id;
    private String email;
    private String userName;
    private String password;
    private String phone;

    public Member(
        Long id,
        String email,
        String userName,
        String password,
        String phone
    ){
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }

}
