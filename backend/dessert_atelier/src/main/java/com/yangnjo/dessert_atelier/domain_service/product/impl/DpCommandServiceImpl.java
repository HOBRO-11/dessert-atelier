package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.repository.product.DisplayProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DpCommandServiceImpl implements DisplayProductCommandService {

    private final DisplayProductRepository displayProductRepository;

    @Override
    public Long create(final DisplayProductCreateDto dto) {
        DisplayProduct displayProduct = dto.toEntity();
        DisplayProduct savedDisplayProduct = displayProductRepository.save(displayProduct);
        return savedDisplayProduct.getId();
    }

    @Override
    public void update(final DisplayProductUpdateDto dto) {
        Long dpId = dto.getDpId();
        String title = dto.getTitle();
        String description = dto.getDesc();
        String thumb = dto.getThumb();

        DisplayProduct displayProduct = findDisplayProductById(dpId);
        if (StringUtils.hasText(title)) {
            displayProduct.setTitle(title);
        }
        if (StringUtils.hasText(description)) {
            displayProduct.setDescription(description);
        }
        if (StringUtils.hasText(thumb)) {
            displayProduct.setThumb(thumb);
        }
    }

    @Override
    public void updateDisplayProductStatus(Long dpId, DisplayProductStatus dpStatus) {
        DisplayProduct displayProduct = findDisplayProductById(dpId);
        displayProduct.setDisplayProductStatus(dpStatus);
    }

    private DisplayProduct findDisplayProductById(Long dpId) {
        DisplayProduct displayProduct = displayProductRepository.findById(dpId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return displayProduct;
    }
}
