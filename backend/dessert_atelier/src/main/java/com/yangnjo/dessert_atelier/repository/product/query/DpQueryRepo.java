package com.yangnjo.dessert_atelier.repository.product.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;

public interface DpQueryRepo {
    List<DpSimpleDto> findAllSimpleByDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption);

    List<DpSimpleDto> findAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption);

    Long countByDpStatus(DisplayProductStatus displayProductStatus);

    Long countByExceptDpStatus(DisplayProductStatus displayProductStatus);

    List<DpSimpleDto> findAllSimpleByIdIn(List<Long> ids);

    Optional<DpDto> findById(Long id);

}