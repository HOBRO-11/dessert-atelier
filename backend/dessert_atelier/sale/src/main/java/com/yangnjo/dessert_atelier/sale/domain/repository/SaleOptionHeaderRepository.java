package com.yangnjo.dessert_atelier.sale.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;

@Repository
public interface SaleOptionHeaderRepository extends JpaRepository<SaleOptionHeader, Long> {
    

    public default boolean existByEntity(SaleOptionHeader optionHeader){
        return existsBySalePageIdAndHeader(optionHeader.getSalePage().getId(), optionHeader.getHeader());
    }


    boolean existsBySalePageIdAndHeader(Long salePageId, String header); 
}
