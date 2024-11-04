package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQueryService;
import com.yangnjo.dessert_atelier.repository.dto.ProductDto;
import com.yangnjo.dessert_atelier.repository.query.ProductQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductQueryRepo productQueryRepo;

    @Override
    public List<ProductDto> getProductsByStatus(ProductStatus status, PageOption pageOption) {
        return productQueryRepo.findByStatus(status, pageOption);
    }
}