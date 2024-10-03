package com.yangnjo.dessert_atelier.domain;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @OneToMany(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Addresses> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "users", orphanRemoval = true)
    private List<Reviews> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "users", orphanRemoval = true)
    private List<QnAs> qnAs = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Orders> orders = new ArrayList<>();

    @OneToOne(mappedBy = "users", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Basket basket;

    public Users(String email, String password, String name, Integer phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.userStatus = UserStatus.ACTIVE;
    }

    public void addAddress(Addresses address) {
        this.addresses.add(address);
        address.setUsers(this);
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
        basket.setUsers(this);
    }

    public void BanUser() {
        this.userStatus = UserStatus.BAN;
    }

    public void ActiveUser() {
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
        reviews.setUsers(this);
    }

    protected void addQnA(QnAs qnAs) {
        this.qnAs.add(qnAs);
    }

    protected void addOrder(Orders orders) {
        this.orders.add(orders);
    }

}
