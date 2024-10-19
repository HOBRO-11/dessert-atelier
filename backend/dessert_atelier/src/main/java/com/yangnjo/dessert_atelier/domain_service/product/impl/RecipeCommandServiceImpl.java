package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.product.Component;
import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.Recipe;
import com.yangnjo.dessert_atelier.domain_service.product.RecipeCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.RecipeCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.RecipeCreateDto.ComponentQuantity;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ComponentNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.RecipeNotFoundException;
import com.yangnjo.dessert_atelier.repository.ComponentRepository;
import com.yangnjo.dessert_atelier.repository.ProductRepository;
import com.yangnjo.dessert_atelier.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class RecipeCommandServiceImpl implements RecipeCommandService {

    private final RecipeRepository recipeRepository;
    private final ComponentRepository componentRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Long> createRecipes(RecipeCreateDto dto) {
        Product product = findProductById(dto.getProductId());

        List<ComponentQuantity> componentQuantities = dto.getComponentQuantities();
        List<Recipe> recipes = componentQuantities.stream()
                .map(cq -> new Recipe(findComponentById(cq.getComponentId()), cq.getQuantity()))
                .collect(Collectors.toList());

        product.addRecipes(recipes);
        return recipeRepository.saveAll(recipes).stream().map(Recipe::getId).collect(Collectors.toList());
    }

    @Override
    public void updateRecipeQuantity(Long recipeId, Integer quantity) {
        Recipe recipe = findRecipeById(recipeId);
        recipe.setQuantity(quantity);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    private Recipe findRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException());
        return recipe;
    }

    private Component findComponentById(Long componentId) {
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new ComponentNotFoundException());
        return component;
    }

    private Product findProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());
        return product;
    }
}
