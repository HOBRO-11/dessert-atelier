package com.yangnjo.dessert_atelier.domain_service.admin.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.admin.AdminRole;
import com.yangnjo.dessert_atelier.domain.admin.AdminStatus;
import com.yangnjo.dessert_atelier.domain.admin.StoreAdmin;
import com.yangnjo.dessert_atelier.domain_service.admin.StoreAdminCommandService;
import com.yangnjo.dessert_atelier.domain_service.admin.dto.StoreAdminCreateDto;
import com.yangnjo.dessert_atelier.domain_service.admin.exception.StoreAdminForbiddenException;
import com.yangnjo.dessert_atelier.domain_service.admin.exception.StoreAdminMustGeOneException;
import com.yangnjo.dessert_atelier.domain_service.admin.exception.StoreAdminNotFoundException;
import com.yangnjo.dessert_atelier.repository.StoreAdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class SaCommandServiceImpl implements StoreAdminCommandService {

  private final StoreAdminRepository storeAdminRepository;
  private static final int STORE_ADMIN_OWNER_MIN_COUNT = 1;

  @Override
  public Long createStoreAdmin(final StoreAdminCreateDto dto) {
    StoreAdmin storeAdmin = dto.toEntity();
    StoreAdmin savedStoreAdmin = storeAdminRepository.save(storeAdmin);
    return savedStoreAdmin.getId();
  }

  @Override
  public void updateStoreAdminPhone(Long adminId, String phone) {
    StoreAdmin storeAdmin = findStoreAdminById(adminId);
    storeAdmin.setPhone(phone);
  }

  @Override
  public void updateStoreAdminPassword(Long adminId, String password) {
    StoreAdmin storeAdmin = findStoreAdminById(adminId);
    storeAdmin.setPassword(password);
  }

  @Override
  public void updateStoreAdminStatus(Long adminId, AdminStatus status) {
    StoreAdmin storeAdmin = findStoreAdminById(adminId);
    storeAdmin.setAdminStatus(status);
    new StoreAdminForbiddenException();
  }

  @Override
  public void updateStoreAdminRole(Long adminId, AdminRole role) {
    StoreAdmin storeAdmin = findStoreAdminById(adminId);
    storeAdmin.setAdminRole(role);
  }

  @Override
  public void deleteStoreAdmin(Long adminId) {
    StoreAdmin sa = findStoreAdminById(adminId);
    AdminRole saRole = sa.getAdminRole();
    if (saRole == AdminRole.OWNER) {
      checkExistOwnerGtOne();
    }
    storeAdminRepository.deleteById(adminId);
  }

  private StoreAdmin findStoreAdminById(Long adminId) {
    StoreAdmin storeAdmin = storeAdminRepository.findById(adminId)
        .orElseThrow(() -> new StoreAdminNotFoundException());
    return storeAdmin;
  }

  private void checkExistOwnerGtOne() {
    boolean existOwnerGtOne = storeAdminRepository.countByAdminRole(AdminRole.OWNER) > STORE_ADMIN_OWNER_MIN_COUNT;
    if (existOwnerGtOne == false) {
      throw new StoreAdminMustGeOneException();
    }
  }
}
