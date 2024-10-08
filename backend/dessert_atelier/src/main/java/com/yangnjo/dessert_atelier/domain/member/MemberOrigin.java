package com.yangnjo.dessert_atelier.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberOrigin {
  KAKAO,
  NAVER,
  GOOGLE,
  STORE;
}
