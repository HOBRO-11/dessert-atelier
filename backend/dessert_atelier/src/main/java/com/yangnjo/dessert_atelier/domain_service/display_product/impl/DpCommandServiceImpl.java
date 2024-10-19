package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.DisplayProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DisplayProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DisplayProductUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class DpCommandServiceImpl implements DisplayProductCommandService {

    private final DisplayProductRepository displayProductRepository;

    @Override
    public Long createDisplayProduct(final DisplayProductCreateDto dto) {
        checkUniqueNaming(dto.getNaming());
        DisplayProduct displayProduct = dto.toEntity();
        DisplayProduct savedDisplayProduct = displayProductRepository.save(displayProduct);
        return savedDisplayProduct.getId();
    }

    @Override
    public void updateDisplayProduct(final DisplayProductUpdateDto dto) {
        Long dpId = dto.getDpId();
        String naming = dto.getNaming();
        String description = dto.getDescription();
        String thumb = dto.getThumb();

        DisplayProduct displayProduct = findDisplayProductById(dpId);
        if (StringUtils.hasText(naming)) {
            checkUniqueNaming(naming);
            displayProduct.setNaming(naming);
        }
        if (StringUtils.hasText(description)) {
            displayProduct.setDescription(description);
        }
        if (StringUtils.hasText(thumb)) {
            displayProduct.setThumb(thumb);
        }

        // Save the updated display product
        displayProductRepository.save(displayProduct);
    }

    @Override
    public void updateDisplayProductSaleStatus(Long dpId, SaleStatus saleStatus) {
        DisplayProduct displayProduct = findDisplayProductById(dpId);
        displayProduct.setSaleStatus(saleStatus);
        
        // Save the updated display product
        displayProductRepository.save(displayProduct);
    }

    /*
     * batch 전용 함수
     */
    @Override
    public void deleteDisplayProduct(Long dpId) {
        displayProductRepository.deleteById(dpId);
    }

    private DisplayProduct findDisplayProductById(Long dpId) {
        DisplayProduct displayProduct = displayProductRepository.findById(dpId)
                .orElseThrow(() -> new DisplayProductNotFountException());
        return displayProduct;
    }

    private void checkUniqueNaming(String naming) {
        boolean existsByNaming = displayProductRepository.existsByNaming(naming);
        if (existsByNaming) {
            throw new DisplayProductAlreadyExistException("해당 별칭 이미 존재합니다.");
        }
    }
}
