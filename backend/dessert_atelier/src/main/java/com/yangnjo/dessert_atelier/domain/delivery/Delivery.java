package com.yangnjo.dessert_atelier.domain.delivery;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.domain.order.Orders;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_code", nullable = false)
    private String deliveryCode;

    @JoinColumn(name = "delivery_company_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DeliveryCompany deliveryCompany;

    @JoinColumn(name = "order_code")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Orders orders;

    private LocalDateTime createdAt;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus deliveryStatus;

    public Delivery(Orders orders,String deliveryCode, DeliveryCompany deliveryCompany) {
        this.orders = orders;
        this.deliveryCode = deliveryCode;
        this.deliveryCompany = deliveryCompany;
        this.deliveryStatus = DeliveryStatus.IN_PROGRESS;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

}
