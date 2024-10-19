package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.ProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public Long createProduct(final ProductCreateDto dto) {
        checkUniqueName(dto.getName());
        Product product = dto.toEntity();
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Override
    public void updateProduct(final ProductUpdateDto dto) {
        Long productId = dto.getProductId();
        String name = dto.getName();
        Integer price = dto.getPrice();
        String thumb = dto.getThumb();

        Product product = findProductById(productId);
        
        if (name != null && (name.isEmpty() == false)) {
            product.setName(name);
        }
        if (price != 0) {
            product.setPrice(price);
        }
        if (thumb != null) {
            product.setThumb(thumb);
        }
    }

    @Override
    public void updateProductStatus(Long productId, ProductStatus status) {
        Product product = findProductById(productId);
        if (status != null) {
            product.setProductStatus(status);
        }
    }
    
    /*
     * batch 전용 함수
     */
    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product findProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());
        return product;
    }


    private void checkUniqueName(String name) {
        boolean isExist = productRepository.existsByName(name);
        if (isExist) {
            throw new ProductAlreadyExistException();
        }
    }
}
