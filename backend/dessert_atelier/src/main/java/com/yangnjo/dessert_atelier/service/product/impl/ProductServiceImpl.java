package com.yangnjo.dessert_atelier.service.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_service.product.ProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.service.product.ProductService;
import com.yangnjo.dessert_atelier.service.product.dto.ProductCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.ProductUpdateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    @Override
    public Long create(ProductCreateForm form) {
        return productCommandService.create(form.toDto());
    }

    @Override
    public void update(ProductUpdateForm dto) {
        productCommandService.update(dto.toDto());
    }

    @Override
    @Transactional
    public void setQuantity(Long id, int quantity) {
        productCommandService.setQuantity(id, quantity);
    }

    @Override
    public List<ProductDto> get(PageOption pageOption) {
        return productQueryService.get(pageOption);
    }

    @Override
    public Optional<ProductDto> getByProductId(Long productId) {
        return productQueryService.getByProductId(productId);
    }
    
}
