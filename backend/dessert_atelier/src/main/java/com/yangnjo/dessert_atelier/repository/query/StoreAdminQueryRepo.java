package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.AdminDto;

public interface StoreAdminQueryRepo {

  List<AdminDto> findAll();

  Optional<AdminDto> find(Long adminId);

}