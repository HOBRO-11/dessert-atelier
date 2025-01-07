package com.yangnjo.dessert_atelier.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.product.ProductQuantity;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {

    public default boolean existByEntity(ProductQuantity entity) {
        Long optionId = entity.getProductOption().getId();
        Long productId = entity.getProduct().getId();
        Integer quantity = entity.getQuantity();
        return existsByProductOptionIdAndProductIdAndQuantity(optionId, productId, quantity);
    }

    boolean existsByProductOptionIdAndProductIdAndQuantity (Long optionId, Long productId, Integer quantity);
}
