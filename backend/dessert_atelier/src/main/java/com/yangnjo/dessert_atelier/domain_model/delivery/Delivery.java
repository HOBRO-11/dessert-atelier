package com.yangnjo.dessert_atelier.domain_model.delivery;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain_model.order.Orders;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    private String deliveryCode;

    @JoinColumn(name = "order_code")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Orders orders;

    private LocalDateTime createdAt;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    public Delivery(String deliveryCode, Orders orders) {
        this.orders = orders;
        this.deliveryCode = deliveryCode;
        this.deliveryStatus = DeliveryStatus.PREPARING;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setIdToTest(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

}
