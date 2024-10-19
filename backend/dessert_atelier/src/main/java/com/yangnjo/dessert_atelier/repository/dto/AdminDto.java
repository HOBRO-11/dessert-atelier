package com.yangnjo.dessert_atelier.repository.dto;

import static com.yangnjo.dessert_atelier.domain.admin.QStoreAdmin.*;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.domain.admin.AdminRole;
import com.yangnjo.dessert_atelier.domain.admin.AdminStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDto {

  private Long id;
  private String email;
  private String phone;
  private AdminStatus adminStatus;
  private AdminRole adminRole;

  public static Expression<AdminDto> asDto() {
    return Projections.constructor(
        AdminDto.class,
        storeAdmin.id,
        storeAdmin.email,
        storeAdmin.phone,
        storeAdmin.adminStatus,
        storeAdmin.adminRole);
  }
}
