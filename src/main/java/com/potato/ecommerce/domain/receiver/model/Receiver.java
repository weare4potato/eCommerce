package com.potato.ecommerce.domain.receiver.model;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Receiver {

    private Long id;
    private Member member;
    private String name;
    private String phone;
    private String addressName;
    private String city;
    private String street;
    private String detail;
    private String zipcode;
}
