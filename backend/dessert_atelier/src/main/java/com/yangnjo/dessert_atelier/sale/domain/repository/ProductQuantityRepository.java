package com.yangnjo.dessert_atelier.sale.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.sale.domain.entity.ProductQuantity;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {

    public default boolean existByEntity(ProductQuantity entity) {
        Long optionId = entity.getSaleOption().getId();
        Long productId = entity.getProduct().getId();
        Integer quantity = entity.getQuantity();
        return existsBySaleOptionIdAndProductIdAndQuantity(optionId, productId, quantity);
    }

    boolean existsBySaleOptionIdAndProductIdAndQuantity (Long optionId, Long productId, Integer quantity);
}
