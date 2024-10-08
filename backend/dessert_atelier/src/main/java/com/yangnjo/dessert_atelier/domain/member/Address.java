package com.yangnjo.dessert_atelier.domain.member;

import com.yangnjo.dessert_atelier.domain.value_type.Destination;

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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String naming;

    @Setter(value = AccessLevel.PROTECTED)
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Setter
    @Embedded
    private Destination destination;

    @Setter
    @Column(nullable = false)
    private boolean isDefault;

    public Address(String naming, String postCode, String detailAddress, String receiver,
            String phone, boolean isDefault) {
        this.naming = naming;
        Destination newDestination = new Destination(postCode, detailAddress, receiver, phone);
        this.destination = newDestination;
        this.isDefault = isDefault;
    }

}