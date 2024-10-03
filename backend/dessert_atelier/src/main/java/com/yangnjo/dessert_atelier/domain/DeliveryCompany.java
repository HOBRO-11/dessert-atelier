package com.yangnjo.dessert_atelier.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String companyName;

    @Column(nullable = false)
    public int phone;

    public static DeliveryCompany createDeliveryCompany(String companyName, int phone) {
        DeliveryCompany deliveryCompany = new DeliveryCompany();
        deliveryCompany.companyName = companyName;
        deliveryCompany.phone = phone;
        return deliveryCompany;
    }

}
