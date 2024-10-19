package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.RecipeDto;

public interface RecipeQueryRepo {

  List<RecipeDto> findByProductId(Long productId);

}