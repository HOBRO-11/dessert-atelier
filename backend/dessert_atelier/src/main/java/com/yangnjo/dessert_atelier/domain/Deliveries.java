package com.yangnjo.dessert_atelier.domain;

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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deliveries {

    @Id
    @Column(name = "delivery_code")
    private String code;

    @Setter(value = AccessLevel.PROTECTED)
    @OneToOne(mappedBy = "deliveries", fetch = FetchType.LAZY, optional = false)
    private Orders orders;

    @JoinColumn(name = "delivery_company", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DeliveryCompany company;

    private LocalDateTime createdAt;

    public Deliveries(String code, DeliveryCompany company) {
        this.code = code;
        this.company = company;
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
