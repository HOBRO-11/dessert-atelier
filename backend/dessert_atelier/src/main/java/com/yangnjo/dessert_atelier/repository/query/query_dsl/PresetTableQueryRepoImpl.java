package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.display_product.QPresetTable.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.PresetTableDto;
import com.yangnjo.dessert_atelier.repository.query.PresetTableQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PresetTableQueryRepoImpl implements PresetTableQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<PresetTableDto> findByDpId(Long dpId) {
    return Optional.ofNullable(queryFactory.select(PresetTableDto.asDto())
        .from(presetTable)
        .where(equalDpId(dpId))
        .fetchOne());
  }

  @Override
  public List<PresetTableDto> findAll() {
    return queryFactory.select(PresetTableDto.asDto())
        .from(presetTable)
        .orderBy(byNumbering())
        .fetch();
  }

  private OrderSpecifier<Integer> byNumbering() {
    return presetTable.numbering.asc();
  }

  private BooleanExpression equalDpId(Long dpId) {
    return presetTable.displayProduct.id.eq(dpId);
  }
}
