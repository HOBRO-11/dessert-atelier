package com.yangnjo.dessert_atelier.sale.domain.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.ShopPageDto;

public interface ShopPageQueryService {

    List<ShopPageDto> findAll();

}