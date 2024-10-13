package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.product.QRecipe.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.RecipeDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RecipeQueryRepoImpl {
  private final JPAQueryFactory queryFactory;

  public List<RecipeDto> findByProductId(Long productId) {
    return queryFactory.select(RecipeDto.asDto())
        .from(recipe)
        .where(recipe.product.id.eq(productId))
        .fetch();
  }
}
