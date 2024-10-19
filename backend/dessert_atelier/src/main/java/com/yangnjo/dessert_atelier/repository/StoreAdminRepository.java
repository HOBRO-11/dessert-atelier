package com.yangnjo.dessert_atelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain.admin.AdminRole;
import com.yangnjo.dessert_atelier.domain.admin.StoreAdmin;

@Repository
public interface StoreAdminRepository extends JpaRepository<StoreAdmin, Long> {

  Long countByAdminRole(AdminRole owner);

}
