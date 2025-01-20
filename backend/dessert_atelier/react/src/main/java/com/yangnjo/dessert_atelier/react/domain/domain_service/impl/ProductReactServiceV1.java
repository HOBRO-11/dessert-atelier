package com.yangnjo.dessert_atelier.react.domain.domain_service.impl;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.react.domain.domain_service.ProductReactService;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReact;
import com.yangnjo.dessert_atelier.react.domain.repository.ProductReactRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductReactServiceV1 implements ProductReactService {

    private final ProductReactRepository productReactRepository;

    @Override
    public Long create() {
        return productReactRepository.save(new ProductReact()).getId();
    }
}
