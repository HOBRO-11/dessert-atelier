package com.yangnjo.dessert_atelier.domain_model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    MEMBER("member"), ADMIN("admin"), STAFF("staff"), PART_TIME("partTime");

    private final String role;
}
