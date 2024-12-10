package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.repository.product.query.ProductQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductQueryRepo productQueryRepo;

    @Override
    public List<ProductDto> get(PageOption pageOption) {
        return productQueryRepo.find(pageOption);
    }

    @Override
    public Optional<ProductDto> getByProductId(Long productId) {
        return productQueryRepo.findByProductId(productId);
    }

}
