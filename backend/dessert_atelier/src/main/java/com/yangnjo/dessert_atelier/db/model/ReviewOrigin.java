package com.yangnjo.dessert_atelier.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewOrigin {

    NAVER_STORE("네이버 스토어 구매자"),
    IDIUS("아이디어스 스토어 구매자"),
    THIS("본 스토어 구매자");

    private final String title;
}
