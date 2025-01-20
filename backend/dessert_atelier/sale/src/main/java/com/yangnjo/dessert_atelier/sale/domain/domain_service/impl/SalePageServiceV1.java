package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.sale.domain.domain_service.SalePageService;
import com.yangnjo.dessert_atelier.sale.domain.entity.SalePage;
import com.yangnjo.dessert_atelier.sale.domain.repository.SalePageRepository;
import com.yangnjo.dessert_atelier.sale.dto.SalePageCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.SalePageNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalePageServiceV1 implements SalePageService {

    private final SalePageRepository salePageRepo;

    @Override
    public Long create(final SalePageCreateDto dto) {
        SalePage salePage = dto.toEntity();
        SalePage oldSalePage = salePageRepo.save(salePage);
        return oldSalePage.getId();
    }

    @Override
    public void updateContent(Long salePageId, List<String> content) {
        SalePage salePage = findSalePageById(salePageId);
        salePage.setContent(content);
    }

    @Override
    public void updateProductReactId(Long salePageId, Long productReactId) {
        SalePage salePage = findSalePageById(salePageId);
        salePage.setProductReactId(productReactId);
    }

    @Override
    public void updateSubtitle(Long salePageId, List<String> subtitle) {
        SalePage salePage = findSalePageById(salePageId);
        salePage.setSubtitles(subtitle);
    }

    @Override
    public void updateThumbnail(Long salePageId, List<String> thumbnail) {
        SalePage salePage = findSalePageById(salePageId);
        salePage.setThumbnail(thumbnail);
    }

    private SalePage findSalePageById(Long salePageId) {
        return salePageRepo.findById(salePageId).orElseThrow(SalePageNotFoundException::new);
    }
}
