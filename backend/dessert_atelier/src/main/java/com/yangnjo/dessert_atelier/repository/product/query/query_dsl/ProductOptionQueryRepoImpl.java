package com.yangnjo.dessert_atelier.repository.product.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_model.product.QProductOption;
import com.yangnjo.dessert_atelier.domain_model.product.QProductQuantity;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionDto;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionDto.ProductQuantityDto;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.query.ProductOptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOptionQueryRepoImpl implements ProductOptionQueryRepo {

    private final JPAQueryFactory queryFactory;
    QProductOption productOption = QProductOption.productOption;
    QProductQuantity productQuantity = QProductQuantity.productQuantity;

    // @Override
    // public List<OptionSimpleDto> findAllSimpleByDpIdAndStatus(Long dpId,
    // OptionStatus status, PageOption pageOption) {
    // return queryFactory
    // .select(OptionSimpleDto.asDto())
    // .from(option)
    // .where(equalStatus(status), equalDpId(dpId))
    // .limit(pageOption.getSize())
    // .offset(pageOption.getOffset())
    // .orderBy(pageOption.getDirection(option.id))
    // .fetch();
    // }

    @Override
    public Long countByOptionHeaderIdAndStatus(Long optionHeaderId, OptionStatus status) {
        return queryFactory
                .select(productOption.count())
                .from(productOption)
                .where(equalOptionHeaderId(optionHeaderId), equalStatus(status))
                .fetchOne();
    }

    @Override
    public List<ProductOptionSimpleDto> findAllSimpleByOptionHeaderIdAndStatus(Long optionHeaderId, OptionStatus status,
            PageOption pageOption) {
        return queryFactory
                .select(ProductOptionSimpleDto.asDto())
                .from(productOption)
                .where(equalOptionHeaderId(optionHeaderId), equalStatus(status))
                .limit(pageOption.getSize())
                .offset(pageOption.getOffset())
                .orderBy(pageOption.getDirection(productOption.id))
                .fetch();
    }

    @Override
    public boolean existsByOptionHeaderIdInAndStatus(List<Long> optionHeaderIds, OptionStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status를 입력해주세요.");
        }

        for (Long optionHeaderId : optionHeaderIds) {
            if (optionHeaderId == null) {
                throw new IllegalArgumentException("optionHeaderId를 입력해주세요.");
            }

            boolean exists = queryFactory
                    .select(productOption.id)
                    .from(productOption)
                    .where(equalOptionHeaderId(optionHeaderId), equalStatus(status))
                    .fetchFirst() != null;

            if (exists == false) {
                return false;
            }
        }
        return true;
    }

    public BooleanExpression equalOptionHeaderId(Long optionHeaderId) {
        if (optionHeaderId == null) {
            throw new IllegalArgumentException("optionHeaderId를 입력해주세요.");
        }
        return productOption.productOptionHeader.id.eq(optionHeaderId);
    }

    @Override
    public List<ProductOptionSimpleDto> findAllSimpleByOptionIdIn(List<Long> optionIds) {
        return queryFactory
                .select(ProductOptionSimpleDto.asDto())
                .from(productOption)
                .where(inOptionIds(optionIds))
                .fetch();
    }

    @Override
    public Optional<ProductOptionDto> findByOptionId(Long optionId) {
        ProductOptionDto dto = queryFactory
                .select(ProductOptionDto.asIncompleteDto())
                .from(productOption)
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

    // @Override
    // public Long countByDpIdAndStatus(Long dpId, OptionStatus status) {
    // return queryFactory.select(option.count())
    // .from(option)
    // .where(equalDpId(dpId), equalStatus(status))
    // .fetchOne();
    // }

    private BooleanExpression inOptionIds(List<Long> optionIds) {
        return productOption.id.in(optionIds);
    }

    private BooleanExpression equalOptionId(Long optionId) {
        return productOption.id.eq(optionId);
    }

    // private BooleanExpression equalDpId(Long dpId) {
    // return option.displayProduct.id.eq(dpId);
    // }

    private BooleanExpression equalProductIdAtProductQuantity(Long optionId) {
        return productQuantity.productOption.id.eq(optionId);
    }

    private BooleanExpression equalStatus(OptionStatus status) {
        return status != null ? productOption.optionStatus.eq(status) : null;
    }
}
