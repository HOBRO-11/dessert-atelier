package com.yangnjo.dessert_atelier.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.domain_model.product.ProductOptionHeader;

@Repository
public interface ProductOptionHeaderRepository extends JpaRepository<ProductOptionHeader, Long> {
    

    public default boolean existByEntity(ProductOptionHeader optionHeader){
        return existsByDisplayProductIdAndOptionHeaderName(optionHeader.getDisplayProduct().getId(), optionHeader.getOptionHeaderName());
    }


    boolean existsByDisplayProductIdAndOptionHeaderName(Long dpId, String optionHeaderName);
}
