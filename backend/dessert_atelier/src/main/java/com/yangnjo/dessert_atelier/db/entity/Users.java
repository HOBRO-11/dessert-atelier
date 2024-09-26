package com.yangnjo.dessert_atelier.db.entity;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users", orphanRemoval = true)
    private List<Addresses> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "users", orphanRemoval = true)
    private List<Reviews> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "users", orphanRemoval = true)
    private List<QnAs> qnAs = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Orders> orders = new ArrayList<>();

    public static Users createUser(String email, String password, String name, Integer phone) {
        Users users = new Users();
        users.email = email;
        users.password = password;
        users.name = name;
        users.phone = phone;
        users.userStatus = UserStatus.ACTIVE;
        return users;
    }

    public void addAddress(Addresses address) {
        this.addresses.add(address);
    }

    public void BanUser() {
        this.userStatus = UserStatus.BAN;
    }

    public void ActiveUser(Users user) {
        this.userStatus = UserStatus.ACTIVE;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changePhone(int phone) {
        this.phone = phone;
    }

    protected void addReview(Reviews reviews) {
        this.reviews.add(reviews);
    }

    public void removeReview(Reviews reviews) {
        this.reviews.remove(reviews);
    }

    public void addQnA(QnAs qnAs) {
        this.qnAs.add(qnAs);
    }

    public void removeQnA(QnAs qnAs) {
        this.qnAs.remove(qnAs);
    }

    protected void addOrder(Orders orders) {
        this.orders.add(orders);
    }

}
