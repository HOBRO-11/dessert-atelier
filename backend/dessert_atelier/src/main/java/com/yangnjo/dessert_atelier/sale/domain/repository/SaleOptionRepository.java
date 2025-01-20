package com.yangnjo.dessert_atelier.sale.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;

@Repository
public interface SaleOptionRepository extends JpaRepository<SaleOption, Long> {
    default boolean existByEntity(SaleOption saleOption) {
        return existsBySaleOptionHeaderAndExplanationAndStatus(saleOption.getSaleOptionHeader(),
                saleOption.getExplanation(), saleOption.getStatus());
    }

    boolean existsBySaleOptionHeaderAndExplanationAndStatus(SaleOptionHeader saleOptionHeader, String explanation,
            SaleOptionStatus status);
}
