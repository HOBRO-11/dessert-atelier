package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain_model.model.ImageSrc;
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
        String description = dto.getDesc();
        String thumb = dto.getThumb();
        Integer optionLayer = dto.getOptionLayer();
        List<ImageSrc> images = dto.getImages();

        DisplayProduct displayProduct = findDisplayProductById(dpId);
        if (StringUtils.hasText(description)) {
            displayProduct.setDescription(description);
            return;
        }
        if (StringUtils.hasText(thumb)) {
            displayProduct.setThumb(thumb);
            displayProduct.setImages(images);
            return;
        }
        if (optionLayer != null) {
            if(optionLayer < 1) {
                throw new IllegalArgumentException("옵션 레이어는 1 이상이어야 합니다.");
            }
            displayProduct.setOptionLayer(optionLayer);
            return;
        }
        if (images != null) {
            displayProduct.setImages(images);
            return;
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
