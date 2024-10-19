package com.yangnjo.dessert_atelier.domain_service.admin.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.admin.StoreAdminQueryService;
import com.yangnjo.dessert_atelier.domain_service.admin.exception.StoreAdminNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.AdminDto;
import com.yangnjo.dessert_atelier.repository.query.StoreAdminQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaQueryServiceImpl implements StoreAdminQueryService {

  private final StoreAdminQueryRepo adminQueryRepo;

  @Override
  public AdminDto get(Long adminId) {
    return adminQueryRepo.find(adminId).orElseThrow(() -> new StoreAdminNotFoundException());
  }

  @Override
  public List<AdminDto> getAll() {
    return adminQueryRepo.findAll();
  }
}
