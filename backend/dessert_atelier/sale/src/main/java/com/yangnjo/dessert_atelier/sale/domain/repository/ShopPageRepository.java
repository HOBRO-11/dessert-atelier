package com.yangnjo.dessert_atelier.sale.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.sale.domain.entity.ShopPage;

@Repository
public interface ShopPageRepository extends JpaRepository<ShopPage, Long> {

    Optional<ShopPage> findByIsDefault(boolean isDefault);

    Optional<ShopPage> findByNaming(String naming);

}
