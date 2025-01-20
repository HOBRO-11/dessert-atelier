package com.yangnjo.dessert_atelier.sale.domain.repository.query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.sale.dto.SalePageDto;
import com.yangnjo.dessert_atelier.sale.dto.SalePageSimpleDto;

public interface SalePageQueryService {

    List<SalePageSimpleDto> findAllSimpleByIdIn(Collection<Long> ids);

    Optional<SalePageDto> findById(Long id);

}