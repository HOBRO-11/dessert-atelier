package com.yangnjo.dessert_atelier.domain_model.member;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.model.BaseEntity;
import com.yangnjo.dessert_atelier.domain_model.order.Orders;
import com.yangnjo.dessert_atelier.domain_model.react.QnA;
import com.yangnjo.dessert_atelier.domain_model.react.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberOrigin memberOrigin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole memberRole;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<QnA> qnAs = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();

    public Member(String email, String name, String phone, MemberRole memberRole, MemberOrigin memberOrigin) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.memberRole = memberRole;
        this.memberOrigin = memberOrigin;
        this.memberStatus = MemberStatus.ACTIVE;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
        address.setMember(this);
    }

    public void addReview(Review reviews) {
        this.reviews.add(reviews);
    }

    public void addQnA(QnA qnAs) {
        this.qnAs.add(qnAs);
    }

    public void addOrder(Orders orders) {
        this.orders.add(orders);
    }

}
