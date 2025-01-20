package com.yangnjo.dessert_atelier.sale.domain.repository.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionDto;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionSimpleDto;

public interface SaleOptionQueryService {

    boolean existsByOptionHeaderIdInAndStatus(List<Long> optionHeaderIds, SaleOptionStatus status);

    Page<SaleOptionSimpleDto> findAllSimpleByOptionHeaderIdAndStatus(Long optionHeaderId, SaleOptionStatus status,
            PageOption pageOption);

    Optional<SaleOptionDto> findByOptionId(Long optionId);

    List<SaleOptionSimpleDto> findAllSimpleByOptionIdIn(List<Long> optionIds);

}