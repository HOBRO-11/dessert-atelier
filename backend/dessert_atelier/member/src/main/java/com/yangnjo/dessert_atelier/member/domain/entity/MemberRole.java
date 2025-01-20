package com.yangnjo.dessert_atelier.member.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    MEMBER("member"), ADMIN("admin"), OWNER("owner");

    private final String role;
}
