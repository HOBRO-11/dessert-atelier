package com.yangnjo.dessert_atelier.member.domain.entity;

import com.yangnjo.dessert_atelier.commons.value_type.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(name = "UQ_NAMING" , columnNames = {"member", "naming"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String naming;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Member member;

    @Setter
    @Embedded
    private Address address;

    @Setter
    @Column(nullable = false)
    private boolean isDefault;

    @Builder
    public MemberAddress(String naming, Member member, Address address, boolean isDefault) {
        this.naming = naming;
        this.member = member;
        this.member.addMemberAddress(this);
        this.address = address;
        this.isDefault = isDefault;
    }

    public void setIdToTest(Long id) {
        this.id = id;
    }

}