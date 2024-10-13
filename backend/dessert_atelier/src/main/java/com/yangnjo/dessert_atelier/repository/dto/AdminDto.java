package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.admin.QStoreAdmin.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDto {

  private String email;
  private String phone;
  private String adminStatus;
  private String adminRole;

  public static Expression<AdminDto> asDto() {
    return Projections.constructor(
        AdminDto.class,
        storeAdmin.email,
        storeAdmin.phone,
        storeAdmin.adminStatus,
        storeAdmin.adminRole);
  }
}
