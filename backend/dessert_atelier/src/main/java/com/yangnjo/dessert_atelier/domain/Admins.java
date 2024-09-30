package com.yangnjo.dessert_atelier.domain;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

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

    public String email;

    public String password;

    public Integer phone;

    @Enumerated(value = EnumType.STRING)
    public AdminStatus adminStatus;

    @Enumerated(value = EnumType.STRING)
    public AdminRole adminRole;

    public static Admins createAdmin(String email, String password, Integer phone, AdminStatus adminStatus,
            AdminRole adminRole) {
        Admins admins = new Admins();
        admins.email = email;
        admins.password = password;
        admins.phone = phone;
        admins.adminStatus = AdminStatus.PENDING;
        admins.adminRole = AdminRole.NONE;
        return admins;
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
