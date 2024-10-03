package com.yangnjo.dessert_atelier.domain;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admins extends BaseEntity {

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

    public Integer phone;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    public AdminStatus adminStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    public AdminRole adminRole;

    public Admins(String email, String password, Integer phone, AdminStatus adminStatus, AdminRole adminRole) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.adminStatus = AdminStatus.PENDING;
        this.adminRole = AdminRole.NONE;
    }

    public void allow() {
        this.adminStatus = AdminStatus.ACTIVE;
    }

    public void ban() {
        this.adminStatus = AdminStatus.BAN;
    }

    public void qualifyOwner() {
        this.adminRole = AdminRole.OWNER;
    }

    public void qualifyStaff() {
        this.adminRole = AdminRole.STAFF;
    }

    public void qualifyPartTime() {
        this.adminRole = AdminRole.PART_TIME;
    }

}
