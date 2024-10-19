package com.yangnjo.dessert_atelier.domain_service.admin.dto;

import com.yangnjo.dessert_atelier.domain.admin.StoreAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreAdminCreateDto {
  String email;
  String password;
  String phone;

  public StoreAdmin toEntity() {
    return new StoreAdmin(email, password, phone);
  }
}
