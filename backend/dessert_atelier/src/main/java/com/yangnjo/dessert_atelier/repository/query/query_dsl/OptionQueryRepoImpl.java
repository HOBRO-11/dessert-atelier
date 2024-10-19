package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.display_product.QOption.*;
import static com.yangnjo.dessert_atelier.domain.display_product.QProductQuantity.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.dto.OptionDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionDetailDto.ProductQuantityDto;
import com.yangnjo.dessert_atelier.repository.query.OptionQueryRepo;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OptionQueryRepoImpl implements OptionQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<OptionSimpleDto> findAllByDppIdAndStatus(Long dppId, OptionStatus status,
      PageOption pageOption) {
    return queryFactory.select(OptionSimpleDto.asDto())
        .from(option)
        .where(equalDppId(dppId), equalStatus(status))
        .offset(pageOption.getOffset())
        .limit(pageOption.getSize())
        .orderBy(pageOption.getDirection(option.optionLayer))
        .fetch();
  }

  @Override
  public Long countByDppIdAndStatus(Long dppId, OptionStatus status) {
    return queryFactory.select(option.count())
        .from(option)
        .where(equalDppId(dppId), equalStatus(status))
        .fetchOne();
  }

  @Override
  public Optional<OptionDetailDto> findDetailByOptionId(Long optionId) {
    OptionDetailDto dto = queryFactory
        .select(OptionDetailDto.asIncompleteDto())
        .from(option)
        .where(equalOptionId(optionId))
        .fetchOne();
    if (dto == null) {
      return Optional.empty();
    }

    List<ProductQuantityDto> dtos = queryFactory
        .select(ProductQuantityDto.asDto())
        .from(productQuantity)
        .where(equalProductIdAtProductQuantity(optionId))
        .fetch();
    if (dtos != null && (dtos.isEmpty() == false)) {
      dto.setProductQuantityDtos(dtos);
    }
    return Optional.of(dto);
  }

  private BooleanExpression equalOptionId(Long optionId) {
    return option.id.eq(optionId);
  }

  private BooleanExpression equalProductIdAtProductQuantity(Long optionId) {
    return productQuantity.option.id.eq(optionId);
  }

  private BooleanExpression equalStatus(OptionStatus status) {
    return status != null ? option.optionStatus.eq(status) : null;
  }

  private BooleanExpression equalDppId(Long dppId) {
    return dppId != null ? option.displayProductPreset.id.eq(dppId) : null;
  }

}
