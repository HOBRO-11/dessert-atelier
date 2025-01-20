package com.yangnjo.dessert_atelier.react.domain.repository.query.query_dsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductQnAStatus;
import com.yangnjo.dessert_atelier.react.domain.entity.QProductQna;
import com.yangnjo.dessert_atelier.react.domain.repository.query.ProductQnaQueryService;
import com.yangnjo.dessert_atelier.react.dto.ProductQnADto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductQnaQueryServiceV1 implements ProductQnaQueryService {

    private final JPAQueryFactory queryFactory;
    QProductQna productQna = QProductQna.productQna;

    @Override
    public List<ProductQnADto> findAllByProductReactIdAndStatus(Long productReactId, PageOption pageOption,
            ProductQnAStatus... statuses) {
        return queryFactory.select(ProductQnADto.asDto())
                .from(productQna)
                .where(equalMemberReactId(productReactId), inStatus(statuses))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(productQna.id))
                .fetch();
    }

    @Override
    public List<ProductQnADto> findAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
        return queryFactory.select(ProductQnADto.asDto())
                .from(productQna)
                .where(equalMemberId(memberId), PeriodOption.betweenLDT(productQna.createdAt, periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(productQna.id))
                .fetch();
    }

    @Override
    public Long countAllByMemberId(Long memberId) {
        return queryFactory.select(productQna.count())
                .from(productQna)
                .where(equalMemberId(memberId))
                .fetchOne();
    }

    @Override
    public Long countAllByProductReactIdAndStatus(Long productReactId, ProductQnAStatus... statuses) {
        return queryFactory.select(productQna.count())
                .from(productQna)
                .where(equalMemberReactId(productReactId), inStatus(statuses))
                .fetchOne();
    }

    private BooleanExpression equalMemberReactId(Long productReactId) {
        if (productReactId == null) {
            throw new IllegalArgumentException("productReact를 입력해주세요.");
        }
        return productQna.productReact.id.eq(productReactId);
    }

    private BooleanExpression inStatus(ProductQnAStatus... statuses) {
        if (statuses == null || statuses.length == 0) {
            throw new IllegalArgumentException("status를 입력해주세요.");
        }
        return productQna.status.in(statuses);
    }

    private BooleanExpression equalMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId를 입력해주세요.");
        }
        return productQna.memberId.eq(memberId);
    }
}
