package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.sale.domain.domain_service.ProductService;
import com.yangnjo.dessert_atelier.sale.domain.entity.Product;
import com.yangnjo.dessert_atelier.sale.domain.repository.ProductRepository;
import com.yangnjo.dessert_atelier.sale.dto.ProductCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.ProductAlreadyExistException;
import com.yangnjo.dessert_atelier.sale.exception.ProductNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceV1 implements ProductService {

    private final ProductRepository productRepo;

    @Override
    public Long create(final ProductCreateDto dto) {
        checkUniqueName(dto.getName());
        Product product = dto.toEntity();
        Product savedProduct = productRepo.save(product);
        return savedProduct.getId();
    }

    private Product findProductById(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    private void checkUniqueName(String name) {
        boolean isExist = productRepo.existsByName(name);
        if (isExist) {
            throw new ProductAlreadyExistException();
        }
    }

    @Override
    public void setStockQuantity(Long id, int quantity) {
        Product product = findProductById(id);

        if (quantity < 0) {
            product.setStockQuantity(0);
            return;
        }

        product.setStockQuantity(quantity);
    }

    @Override
    public void updatePrice(Long productId, int price) {
        Product product = findProductById(productId);
        product.setPrice(price);

    }

}
