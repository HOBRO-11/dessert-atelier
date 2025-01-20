package com.yangnjo.dessert_atelier.sale.domain.repository.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.sale.dto.saleOptionHeaderDto;

public interface SaleOptionHeaderQueryService {

    Optional<saleOptionHeaderDto> findById(Long optionHeaderId);

    Page<saleOptionHeaderDto> findAllBySalePageId(Long salePageId, PageOption pageOption);

    List<saleOptionHeaderDto> findAllByOptionHeaderIdIn(List<Long> optionHeaderIds);

}