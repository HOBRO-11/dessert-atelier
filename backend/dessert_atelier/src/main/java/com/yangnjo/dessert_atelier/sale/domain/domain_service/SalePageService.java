package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.SalePageCreateDto;

public interface SalePageService {
    Long create(SalePageCreateDto dto);

    void updateProductReactId(Long salePageId, Long productReactId);

    void updateSubtitle(Long salePageId, List<String> subtitle);

    void updateThumbnail(Long salePageId, List<String> thumbnail);

    void updateContent(Long salePageId, List<String> content);
}