package com.yangnjo.dessert_atelier.member.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.commons.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
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
    @Column
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
    private MemberStatus status;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberAddress> memberAddresses = new ArrayList<>();

    @Builder
    public Member(String email, String name, String phone, MemberOrigin memberOrigin, MemberRole memberRole,
            MemberStatus status) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.memberOrigin = memberOrigin;
        this.memberRole = memberRole;
        this.status = status;
    }

    public void addMemberAddress(MemberAddress address) {
        this.memberAddresses.add(address);
    }

}
