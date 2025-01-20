package com.yangnjo.dessert_atelier.sale.domain.repository.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PageResponse;
import com.yangnjo.dessert_atelier.sale.domain.entity.QProductQuantity;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;
import com.yangnjo.dessert_atelier.sale.domain.repository.query.SaleOptionQueryService;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionDto;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionDto.ProductQuantityDto;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SaleOptionQueryServiceV1 implements SaleOptionQueryService {

    private final JPAQueryFactory queryFactory;
    QSaleOption saleOption = QSaleOption.saleOption;
    QProductQuantity productQuantity = QProductQuantity.productQuantity;

    @Override
    public Page<SaleOptionSimpleDto> findAllSimpleByOptionHeaderIdAndStatus(Long optionHeaderId,
            SaleOptionStatus status,
            PageOption pageOption) {
        List<SaleOptionSimpleDto> content = queryFactory
                .select(SaleOptionSimpleDto.asDto())
                .from(saleOption)
                .where(equalOptionHeaderId(optionHeaderId), equalOptionStatus(status))
                .limit(pageOption.getSize())
                .offset(pageOption.getOffset())
                .orderBy(pageOption.getDirection(saleOption.id))
                .fetch();

        return PageResponse.of(content, pageOption,
                () -> count(optionHeaderId, status));
    }

    private Long count(Long optionHeaderId, SaleOptionStatus status) {
        return queryFactory
                .select(saleOption.count())
                .from(saleOption)
                .where(equalOptionHeaderId(optionHeaderId), equalOptionStatus(status))
                .fetchOne();
    }

    @Override
    public boolean existsByOptionHeaderIdInAndStatus(List<Long> optionHeaderIds, SaleOptionStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status를 입력해주세요.");
        }

        for (Long optionHeaderId : optionHeaderIds) {
            if (optionHeaderId == null) {
                throw new IllegalArgumentException("optionHeaderId를 입력해주세요.");
            }

            boolean exists = queryFactory
                    .select(saleOption.id)
                    .from(saleOption)
                    .where(equalOptionHeaderId(optionHeaderId), equalOptionStatus(status))
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
        return saleOption.saleOptionHeader.id.eq(optionHeaderId);
    }

    @Override
    public List<SaleOptionSimpleDto> findAllSimpleByOptionIdIn(List<Long> optionIds) {
        return queryFactory
                .select(SaleOptionSimpleDto.asDto())
                .from(saleOption)
                .where(inOptionIds(optionIds))
                .fetch();
    }

    @Override
    public Optional<SaleOptionDto> findByOptionId(Long optionId) {
        SaleOptionDto dto = queryFactory
                .select(SaleOptionDto.asIncompleteDto())
                .from(saleOption)
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

    private BooleanExpression inOptionIds(List<Long> optionIds) {
        if (optionIds == null || optionIds.isEmpty()) {
            throw new IllegalArgumentException("optionIds를 입력해주세요.");
        }
        return saleOption.id.in(optionIds);
    }

    private BooleanExpression equalOptionId(Long optionId) {
        if (optionId == null) {
            throw new IllegalArgumentException("optionId를 입력해주세요.");
        }
        return saleOption.id.eq(optionId);
    }

    private BooleanExpression equalProductIdAtProductQuantity(Long optionId) {
        if (optionId == null) {
            throw new IllegalArgumentException("optionId를 입력해주세요.");
        }
        return productQuantity.saleOption.id.eq(optionId);
    }

    private BooleanExpression equalOptionStatus(SaleOptionStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status를 입력해주세요.");
        }
        return saleOption.status.eq(status);
    }
}
