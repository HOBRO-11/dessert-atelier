package com.yangnjo.dessert_atelier.domain_service.admin;

import com.yangnjo.dessert_atelier.domain.admin.AdminRole;
import com.yangnjo.dessert_atelier.domain.admin.AdminStatus;
import com.yangnjo.dessert_atelier.domain_service.admin.dto.StoreAdminCreateDto;

public interface StoreAdminCommandService {

  Long createStoreAdmin(StoreAdminCreateDto dto);

  void updateStoreAdminPhone(Long adminId, String phone);

  void updateStoreAdminPassword(Long adminId, String password);

  void updateStoreAdminStatus(Long adminId, AdminStatus status);

  void updateStoreAdminRole(Long adminId, AdminRole role);

  void deleteStoreAdmin(Long adminId);

}