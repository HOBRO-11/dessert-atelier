package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_service.product.dto.RecipeCreateDto;

public interface RecipeCommandService {

  List<Long> createRecipes(RecipeCreateDto dto);

  void updateRecipeQuantity(Long recipeId, Integer quantity);

  void deleteRecipe(Long recipeId);

}