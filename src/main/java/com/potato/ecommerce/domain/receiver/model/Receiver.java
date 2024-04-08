package com.potato.ecommerce.domain.receiver.model;

import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;
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
    private String zipcode;
    private String detail;

    public static Receiver fromEntity(ReceiverEntity receiver) {
        return Receiver.builder()
            .id(receiver.getId())
            .member(Member.fromEntity(receiver.getMember()))
            .name(receiver.getName())
            .phone(receiver.getPhone())
            .addressName(receiver.getAddressName())
            .city(receiver.getCity())
            .street(receiver.getStreet())
            .detail(receiver.getDetail())
            .zipcode(receiver.getZipcode())
            .build();
    }

    public ReceiverEntity toEntity() {
        return ReceiverEntity.builder()
            .id(this.id)
            .member(this.member.toEntity())
            .name(this.name)
            .phone(this.phone)
            .addressName(this.addressName)
            .city(this.city)
            .street(this.street)
            .detail(this.detail)
            .zipcode(this.zipcode)
            .build();
    }

    public ReceiverForm createReceiverForm() {
        return ReceiverForm.builder()
            .name(this.name)
            .phone(this.phone)
            .addressName(this.addressName)
            .city(this.city)
            .street(this.street)
            .zipcode(this.zipcode)
            .detail(this.detail).build();
    }

    public void update(ReceiverForm dto) {
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.addressName = dto.getAddressName();
        this.city = dto.getCity();
        this.street = dto.getStreet();
        this.zipcode = dto.getZipcode();
        this.detail = dto.getDetail();
    }

    public boolean isMemberNotMatch(Long memberId) {
        return this.member.isNotMatchMember(memberId);
    }
}
