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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naming;
    
    @Setter(value = AccessLevel.PROTECTED)
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Users users;

    @Embedded
    private Destination destination;

    @Column(nullable = false)
    private boolean isDefault;

    public Addresses(String naming, String postCode, String detailAddress, String receiver, Integer phone, boolean isDefault) {
        this.naming = naming;
        Destination newDestination = new Destination(postCode, detailAddress, receiver, phone);
        this.destination = newDestination;
        this.isDefault = isDefault;
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