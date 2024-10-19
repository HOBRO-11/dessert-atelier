package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.RecipeDto;

public interface RecipeQueryService {

  List<RecipeDto> getRecipesByProductId(Long productId);

}