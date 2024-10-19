package com.yangnjo.dessert_atelier.domain.admin;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreAdmin extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    private String phone;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus adminStatus;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AdminRole adminRole;

    public StoreAdmin(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.adminStatus = AdminStatus.PENDING;
        this.adminRole = AdminRole.NONE;
    }

}
