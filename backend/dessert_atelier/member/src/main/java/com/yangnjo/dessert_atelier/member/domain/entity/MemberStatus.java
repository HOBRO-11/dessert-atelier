package com.yangnjo.dessert_atelier.member.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
    NON_QUALIFIED,
    BAN,
    ACTIVE,
    INACTIVE;
}
