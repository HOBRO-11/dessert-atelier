package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.product.ProductOption;
import com.yangnjo.dessert_atelier.domain_model.product.Product;
import com.yangnjo.dessert_atelier.domain_model.product.ProductQuantity;
import com.yangnjo.dessert_atelier.domain_service.product.ProductQuantityCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductQuantityCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductQuantityAlreadyExistsException;
import com.yangnjo.dessert_atelier.repository.product.OptionRepository;
import com.yangnjo.dessert_atelier.repository.product.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PQCommandServiceImpl implements ProductQuantityCommandService {

    private final ProductQuantityRepository pqRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    @Override
    public void create(List<ProductQuantityCreateDto> dtos) {
        dtos.forEach(dto -> {
            Long optionId = dto.getOptionId();
            Long productId = dto.getProductId();
            Integer quantity = dto.getQuantity();

            if (quantity == null || quantity < 0) {
                throw new IllegalArgumentException("quantity는 양수여야 합니다.");
            }

            ProductOption option = findOptionById(optionId);

            Product product = findProductById(productId);

            ProductQuantity entity = new ProductQuantity(product, option, quantity);

            if (pqRepository.existByEntity(entity)) {
                throw new ProductQuantityAlreadyExistsException();
            }

            pqRepository.save(entity);
        });
    }

    private Product findProductById(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId가 존재하지 않습니다않습니다.");
        }
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }

    private ProductOption findOptionById(Long optionId) {
        if (optionId == null) {
            throw new IllegalArgumentException("optionId가 존재하지 않습니다않습니다.");
        }
        return optionRepository.findById(optionId).orElseThrow(OptionNotFoundException::new);
    }
}
