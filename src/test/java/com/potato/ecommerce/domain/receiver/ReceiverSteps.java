package com.potato.ecommerce.domain.receiver;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import com.potato.ecommerce.domain.receiver.entity.ReceiverEntity;

public class ReceiverSteps {

    static ReceiverEntity createReceiverWithMember(final MemberEntity member) {
        String name = "name";
        String phone = "01011112222";
        String addressName = "addressName";
        String city = "city";
        String street = "street";
        String detail = "detail";
        String zipcode = "zipcode";

        return ReceiverEntity.builder()
            .member(member)
            .name(name)
            .phone(phone)
            .addressName(addressName)
            .city(city)
            .street(street)
            .detail(detail)
            .zipcode(zipcode)
            .build();
    }

    static ReceiverEntity createReceiverWithRequestAndMember(final MemberEntity member,
        final ReceiverForm receiverForm) {
        return ReceiverEntity.builder()
            .member(member)
            .name(receiverForm.getName())
            .phone(receiverForm.getPhone())
            .addressName(receiverForm.getAddressName())
            .city(receiverForm.getCity())
            .street(receiverForm.getStreet())
            .detail(receiverForm.getDetail())
            .zipcode(receiverForm.getZipcode())
            .build();
    }

    static ReceiverForm createReceiverForm() {
        String name = "name";
        String phone = "01011112222";
        String addressName = "addressName";
        String city = "city";
        String street = "street";
        String detail = "detail";
        String zipcode = "zipcode";
        return ReceiverForm.builder()
            .name(name)
            .phone(phone)
            .addressName(addressName)
            .city(city)
            .street(street)
            .zipcode(zipcode)
            .detail(detail)
            .build();
    }
}
