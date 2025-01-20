package com.yangnjo.dessert_atelier.sale.domain.repository.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PageResponse;
import com.yangnjo.dessert_atelier.sale.domain.entity.QSaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.repository.query.SaleOptionHeaderQueryService;
import com.yangnjo.dessert_atelier.sale.dto.saleOptionHeaderDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SaleOptionHeaderQueryServiceV1 implements SaleOptionHeaderQueryService {

    private final JPAQueryFactory queryFactory;
    QSaleOptionHeader saleOptionHeader = QSaleOptionHeader.saleOptionHeader;

    @Override
    public Optional<saleOptionHeaderDto> findById(Long optionHeaderId) {
        return Optional.ofNullable(queryFactory
                .select(saleOptionHeaderDto.asDto())
                .from(saleOptionHeader)
                .where(equalOptionHeaderId(optionHeaderId))
                .fetchOne());
    }

    @Override
    public Page<saleOptionHeaderDto> findAllBySalePageId(Long salePageId, PageOption pageOption) {
        List<saleOptionHeaderDto> content = queryFactory.select(saleOptionHeaderDto.asDto())
                .from(saleOptionHeader)
                .where(equalSalePageId(salePageId))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(saleOptionHeader.id))
                .fetch();

        return PageResponse.of(content, pageOption, () -> count(salePageId));
    }

    private Long count(Long salePageId) {
        return queryFactory.select(saleOptionHeader.count())
                .from(saleOptionHeader)
                .where(equalOptionHeaderId(salePageId))
                .fetchOne();
    }

    @Override
    public List<saleOptionHeaderDto> findAllByOptionHeaderIdIn(List<Long> optionHeaderIds) {
        return queryFactory.select(saleOptionHeaderDto.asDto())
                .from(saleOptionHeader)
                .where(inOptionHeaderIds(optionHeaderIds))
                .fetch();
    }

    private BooleanExpression equalOptionHeaderId(Long optionHeaderId) {
        if (optionHeaderId == null) {
            throw new IllegalArgumentException("optionHeaderId를 입력해주세요.");
        }
        return saleOptionHeader.id.eq(optionHeaderId);
    }

    private BooleanExpression inOptionHeaderIds(List<Long> optionHeaderIds) {
        if (optionHeaderIds == null || optionHeaderIds.isEmpty()) {
            throw new IllegalArgumentException("optionHeaderIds를 입력해주세요.");
        }
        return saleOptionHeader.id.in(optionHeaderIds);
    }

    private BooleanExpression equalSalePageId(Long salePageId) {
        if (salePageId == null) {
            throw new IllegalArgumentException("displayProductId를 입력해주세요.");
        }
        return saleOptionHeader.salePage.id.eq(salePageId);
    }
}
