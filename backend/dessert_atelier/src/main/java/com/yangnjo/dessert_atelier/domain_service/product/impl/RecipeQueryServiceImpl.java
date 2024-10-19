package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.product.RecipeQueryService;
import com.yangnjo.dessert_atelier.repository.dto.RecipeDto;
import com.yangnjo.dessert_atelier.repository.query.RecipeQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeQueryServiceImpl implements RecipeQueryService {

    private final RecipeQueryRepo recipeQueryRepo;

    @Override
    public List<RecipeDto> getRecipesByProductId(Long productId) {
        return recipeQueryRepo.findByProductId(productId);
    }
}
