package com.yangnjo.dessert_atelier.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naming;

    @JoinColumn(name = "users_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users users; // users 테이블의 외래 키로 설정할 수 있습니다.

    @Column(nullable = false)
    private String postCode;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private Integer phone;

    @Column(nullable = false)
    private boolean isDefault;

    public static Address createAddress(Users users, String naming, String postCode, String detailAddress,
            String receiver, Integer phone, boolean isDefault) {
        Address address = new Address();
        address.naming = naming;
        address.users = users;
        users.addAddress(address);
        address.postCode = postCode;
        address.detailAddress = detailAddress;
        address.receiver = receiver;
        address.phone = phone;
        address.isDefault = isDefault;
        return address;
    }

    public boolean setDefaultAddress() {
        this.isDefault = true;
        return true;
    }

    public boolean releaseDefaultAddress() {
        this.isDefault = false;
        return true;
    }

}