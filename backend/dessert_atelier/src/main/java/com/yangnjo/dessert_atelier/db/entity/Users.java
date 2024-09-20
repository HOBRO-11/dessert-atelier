package com.yangnjo.dessert_atelier.db.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yangnjo.dessert_atelier.db.model.BaseEntity;
import com.yangnjo.dessert_atelier.db.model.UserStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users")
    private List<Addresses> addresses = new ArrayList<>();

    public static Users createUser(String email, String password, String name, Integer phone) {
        Users users = new Users();
        users.email = email;
        users.password = password;
        users.name = name;
        users.phone = phone;
        return users;
    }

    public void addAddress(Addresses address){
        this.addresses.add(address);
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
