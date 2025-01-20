package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.sale.domain.domain_service.ProductQuantityService;
import com.yangnjo.dessert_atelier.sale.domain.entity.Product;
import com.yangnjo.dessert_atelier.sale.domain.entity.ProductQuantity;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOption;
import com.yangnjo.dessert_atelier.sale.domain.repository.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.ProductRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionRepository;
import com.yangnjo.dessert_atelier.sale.dto.ProductQuantityCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.sale.exception.ProductQuantityNotFoundException;
import com.yangnjo.dessert_atelier.sale.exception.SaleOptionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQuantityServiceV1 implements ProductQuantityService {

    private final ProductQuantityRepository pqRepository;
    private final SaleOptionRepository saleOptionRepo;
    private final ProductRepository productRepo;

    @Override
    public Long create(ProductQuantityCreateDto dto) {
        Long saleOptionId = dto.getSaleOptionId();
        Long productId = dto.getProductId();
        SaleOption saleOption = findSaleOptionById(saleOptionId);
        Product product = findProductById(productId);
        ProductQuantity entity = dto.toEntity(product, saleOption);
        return pqRepository.save(entity).getId();
    }

    private Product findProductById(Long productId) {
        return productRepo.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    private SaleOption findSaleOptionById(Long saleOptionId) {
        return saleOptionRepo.findById(saleOptionId).orElseThrow(SaleOptionNotFoundException::new);
    }

    @Override
    public void delete(List<Long> productQuantityIds) {
        pqRepository.deleteAllById(productQuantityIds);
    }

    @Override
    public void setQuantity(Long productQuantityId, Integer quantity) {
        ProductQuantity productQuantity = findProductQuantityById(productQuantityId);
        productQuantity.setQuantity(quantity);
    }

    private ProductQuantity findProductQuantityById(Long productQuantityId) {
        return pqRepository.findById(productQuantityId).orElseThrow(ProductQuantityNotFoundException::new);
    }

}
