package com.potato.ecommerce.domain.receiver.entity;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.member.model.Member;
import com.potato.ecommerce.domain.receiver.model.Receiver;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receivers")
public class ReceiverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receiver_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String zipcode;

    public ReceiverEntity(
        MemberEntity member,
        String name,
        String phone,
        String addressName,
        String city,
        String street,
        String detail,
        String zipcode
    ){
        this.member = member;
        this.name = name;
        this.phone = phone;
        this.addressName = addressName;
        this.city = city;
        this.street = street;
        this.detail = detail;
        this.zipcode = zipcode;
    }

    public static ReceiverEntity fromModel(Receiver receiver){
        return ReceiverEntity.builder()
            .id(receiver.getId())
            .member(receiver.getMember())
            .name(receiver.getName())
            .phone(receiver.getPhone())
            .addressName(receiver.getAddressName())
            .city(receiver.getCity())
            .street(receiver.getStreet())
            .detail(receiver.getDetail())
            .zipcode(receiver.getZipcode())
            .build();
    }

    public Receiver toModel(){
        return Receiver.builder()
            .id(this.id)
            .member(this.member)
            .name(this.name)
            .phone(this.phone)
            .addressName(this.addressName)
            .city(this.city)
            .street(this.street)
            .detail(this.detail)
            .zipcode(this.zipcode)
            .build();
    }

}
