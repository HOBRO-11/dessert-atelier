package com.yangnjo.dessert_atelier.domain;

import com.yangnjo.dessert_atelier.domain.vale_type.Destination;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naming;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users users;

    @Embedded
    private Destination destination;

    @Column(nullable = false)
    private boolean isDefault;

    public static Addresses createAddress(Users users, String naming, String postCode, String detailAddress,
            String receiver, Integer phone, boolean isDefault) {
        Addresses address = new Addresses();
        address.naming = naming;
        address.users = users;
        users.addAddress(address);
        Destination newDestination = Destination.createDestination(postCode, detailAddress, receiver, phone);
        address.destination = newDestination;
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