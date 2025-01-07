package com.yangnjo.dessert_atelier.repository.product.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.product.QProductOptionHeader;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductOptionHeaderDto;
import com.yangnjo.dessert_atelier.repository.product.query.ProductOptionHeaderQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class pohQueryRepoImpl implements ProductOptionHeaderQueryRepo {

    private final JPAQueryFactory queryFactory;
    QProductOptionHeader productOptionHeader = QProductOptionHeader.productOptionHeader;

    @Override
    public Optional<ProductOptionHeaderDto> findById(Long optionHeaderId) {
        return Optional.ofNullable(queryFactory
                .select(ProductOptionHeaderDto.asDto())
                .from(productOptionHeader)
                .where(equalOptionHeaderId(optionHeaderId))
                .fetchOne());
    }

    @Override
    public List<ProductOptionHeaderDto> findAllByDpId(Long displayProductId, PageOption pageOption) {
        return queryFactory.select(ProductOptionHeaderDto.asDto())
                .from(productOptionHeader)
                .where(equalDpId(displayProductId))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(productOptionHeader.id))
                .fetch();
    }

    @Override
    public List<ProductOptionHeaderDto> findAllByOptionHeaderIdIn(List<Long> optionHeaderIds) {
        return queryFactory.select(ProductOptionHeaderDto.asDto())
                .from(productOptionHeader)
                .where(inOptionHeaderIds(optionHeaderIds))
                .fetch();
    }

    private BooleanExpression equalOptionHeaderId(Long optionHeaderId) {
        if (optionHeaderId == null) {
            throw new IllegalArgumentException("optionHeaderId를 입력해주세요.");
        }
        return productOptionHeader.id.eq(optionHeaderId);
    }

    private BooleanExpression inOptionHeaderIds(List<Long> optionHeaderIds) {
        if (optionHeaderIds == null || optionHeaderIds.isEmpty()) {
            throw new IllegalArgumentException("optionHeaderIds를 입력해주세요.");
        }
        return productOptionHeader.id.in(optionHeaderIds);
    }

    private BooleanExpression equalDpId(Long displayProductId) {
        if (displayProductId == null) {
            throw new IllegalArgumentException("displayProductId를 입력해주세요.");
        }
        return productOptionHeader.displayProduct.id.eq(displayProductId);
    }
}
