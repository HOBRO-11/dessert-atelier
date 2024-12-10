package com.yangnjo.dessert_atelier.repository.product.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QOption;
import com.yangnjo.dessert_atelier.domain_model.product.QProductQuantity;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionDto.ProductQuantityDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.query.OptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OptionQueryRepoImpl implements OptionQueryRepo {

    private final JPAQueryFactory queryFactory;
    QOption option = QOption.option;
    QProductQuantity productQuantity = QProductQuantity.productQuantity;

    @Override
    public List<OptionSimpleDto> findAllSimpleByDpIdAndStatus(Long dpId, OptionStatus status, PageOption pageOption) {
        return queryFactory
                .select(OptionSimpleDto.asDto())
                .from(option)
                .where(equalStatus(status), equalDpId(dpId))
                .limit(pageOption.getSize())
                .offset(pageOption.getOffset())
                .orderBy(pageOption.getDirection(option.optionLayer))
                .fetch();
    }

    @Override
    public List<OptionSimpleDto> findAllSimpleByOptionIdIn(List<Long> optionIds) {
        return queryFactory
                .select(OptionSimpleDto.asDto())
                .from(option)
                .where(inOptionIds(optionIds))
                .fetch();
    }

    @Override
    public Optional<OptionDto> findByOptionId(Long optionId) {
        OptionDto dto = queryFactory
                .select(OptionDto.asIncompleteDto())
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

    @Override
    public Long countByDpIdAndStatus(Long dpId, OptionStatus status) {
        return queryFactory.select(option.count())
                .from(option)
                .where(equalDpId(dpId), equalStatus(status))
                .fetchOne();
    }

    private BooleanExpression inOptionIds(List<Long> optionIds) {
        return option.id.in(optionIds);
    }

    private BooleanExpression equalOptionId(Long optionId) {
        return option.id.eq(optionId);
    }

    private BooleanExpression equalDpId(Long dpId) {
        return option.displayProduct.id.eq(dpId);
    }

    private BooleanExpression equalProductIdAtProductQuantity(Long optionId) {
        return productQuantity.option.id.eq(optionId);
    }

    private BooleanExpression equalStatus(OptionStatus status) {
        return status != null ? option.optionStatus.eq(status) : null;
    }
}
