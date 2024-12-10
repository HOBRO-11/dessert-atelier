package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;

public interface DisplayProductQueryService {

    Page<DpSimpleDto> getAllSimpleByDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption);

    Page<DpSimpleDto> getAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption);

    List<DpSimpleDto> getAllSimpleByIdIn(List<Long> ids);

    Optional<DpDto> getById(Long id);

}