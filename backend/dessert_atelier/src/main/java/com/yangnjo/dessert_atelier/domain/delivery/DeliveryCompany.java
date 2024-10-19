package com.yangnjo.dessert_atelier.domain.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Setter
    @Column(nullable = false)
    private String phone;

    public DeliveryCompany(String companyName, String phone) {
        this.companyName = companyName;
        this.phone = phone;
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

}
