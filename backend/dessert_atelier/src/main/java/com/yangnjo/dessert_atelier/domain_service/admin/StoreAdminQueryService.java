package com.yangnjo.dessert_atelier.domain_service.admin;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.AdminDto;

public interface StoreAdminQueryService {

  AdminDto get(Long adminId);

  List<AdminDto> getAll();

}