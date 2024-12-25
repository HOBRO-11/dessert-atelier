package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.product.Product;
import com.yangnjo.dessert_atelier.domain_service.product.ProductCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public Long create(final ProductCreateDto dto) {
        checkUniqueName(dto.getName());
        Product product = dto.toEntity();
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    @Override
    public void update(final ProductUpdateDto dto) {
        Long productId = dto.getProductId();
        Integer price = dto.getPrice();
        String thumb = dto.getThumb();

        Product product = findProductById(productId);

        if (price != 0) {
            product.setPrice(price);
        }
        if (thumb != null) {
            product.setThumb(thumb);
        }
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    private void checkUniqueName(String name) {
        boolean isExist = productRepository.existsByName(name);
        if (isExist) {
            throw new ProductAlreadyExistException();
        }
    }

    @Override
    public void setQuantity(Long id, int quantity) {
        Product product = findProductById(id);

        if (quantity < 0) {
            product.setQuantity(0);
            return;
        }

        product.setQuantity(quantity);
    }

}
