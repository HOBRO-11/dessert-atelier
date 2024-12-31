package com.yangnjo.dessert_atelier.service.product;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityUpdateForm;

public interface DisplayProductService {

    Long create(DisplayProductEntityCreateForm form);

    void updateThumbs(DisplayProductEntityUpdateForm form);

    void updateDescription(DisplayProductEntityUpdateForm form);

    void updateOptionLayer(DisplayProductEntityUpdateForm form);

    void updateDisplayProductStatus(Long dpId, DisplayProductStatus saleStatus);

    Page<DpSimpleDto> getAllSimpleByDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption);

    Page<DpSimpleDto> getAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption);

    Optional<DpDto> getById(Long id);
}
