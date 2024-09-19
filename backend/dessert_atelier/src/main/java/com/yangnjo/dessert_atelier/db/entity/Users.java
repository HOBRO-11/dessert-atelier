package com.yangnjo.dessert_atelier.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yangnjo.dessert_atelier.db.model.BaseEntity;
import com.yangnjo.dessert_atelier.db.model.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Users extends BaseEntity {

    // @Email
    @Column(nullable = false, unique = true)
    private String email;

    // @Size(min = 12)
    @JsonIgnore
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{12,}$")
    @Column(nullable = false)
    private String password;

    // @Size(min = 2)
    @Column(nullable = false)
    private String name;

    // @Pattern(regexp = "^\\d{8}$")
    @Column(nullable = false)
    private Integer phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;

    public static Users createUser(String email, String password, String name, Integer phone) {
        Users users = new Users();
        users.email = email;
        users.password = password;
        users.name = name;
        users.phone = phone;
        return users;
    }

    public boolean BanUser(Users user) {
        user.userStatus = UserStatus.BAN;
        return true;
    }

    public boolean ActiveUser(Users user) {
        user.userStatus = UserStatus.BAN;
        return true;
    }

}
