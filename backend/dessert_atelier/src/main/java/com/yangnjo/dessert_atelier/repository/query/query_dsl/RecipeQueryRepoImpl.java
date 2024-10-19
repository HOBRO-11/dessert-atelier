package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.product.QRecipe.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.RecipeDto;
import com.yangnjo.dessert_atelier.repository.query.RecipeQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RecipeQueryRepoImpl implements RecipeQueryRepo {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<RecipeDto> findByProductId(Long productId) {
    return queryFactory.select(RecipeDto.asDto())
        .from(recipe)
        .where(recipe.product.id.eq(productId))
        .fetch();
  }
}
