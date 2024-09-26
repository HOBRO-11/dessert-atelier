package com.yangnjo.dessert_atelier.db.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deliveries {

    @Id
    @Column(name = "delivery_code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY, optional = false, mappedBy = "deliveries")
    private Orders orders;

    @JoinColumn(name = "delivery_company")
    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryCompany company;

    private LocalDateTime createdAt;

    public Deliveries createDelivery(String code, Orders orders, DeliveryCompany company) {
        Deliveries deliveries = new Deliveries();
        deliveries.code = code;
        deliveries.orders = orders;
        orders.setDelivery(deliveries);
        deliveries.company = company;
        return deliveries;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
