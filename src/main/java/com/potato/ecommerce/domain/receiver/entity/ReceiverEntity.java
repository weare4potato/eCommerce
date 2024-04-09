package com.potato.ecommerce.domain.receiver.entity;

import com.potato.ecommerce.domain.member.entity.MemberEntity;
import com.potato.ecommerce.domain.receiver.dto.ReceiverForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public ReceiverEntity(MemberEntity member, String name, String phone, String addressName,
        String city, String street, String detail, String zipcode) {
        this.member = member;
        this.name = name;
        this.phone = phone;
        this.addressName = addressName;
        this.city = city;
        this.street = street;
        this.detail = detail;
        this.zipcode = zipcode;
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
