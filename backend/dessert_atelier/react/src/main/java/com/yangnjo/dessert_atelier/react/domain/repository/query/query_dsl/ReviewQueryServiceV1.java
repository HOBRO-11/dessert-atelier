package com.yangnjo.dessert_atelier.react.domain.repository.query.query_dsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductReviewStatus;
import com.yangnjo.dessert_atelier.react.domain.entity.QProductReview;
import com.yangnjo.dessert_atelier.react.domain.repository.query.ProductReviewQueryService;
import com.yangnjo.dessert_atelier.react.dto.ProductReviewDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewQueryServiceV1 implements ProductReviewQueryService {

    private final JPAQueryFactory queryFactory;
    QProductReview productReview = QProductReview.productReview;

    @Override
    public List<ProductReviewDto> findAllByProductReactIdAndStatus(Long productReactId,
            PageOption pageOption, ProductReviewStatus... statuses) {
        return queryFactory.select(ProductReviewDto.asDto())
                .from(productReview)
                .where(equalMemberReactId(productReactId), inReviewStatus(statuses))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(productReview.createdAt.desc())
                .fetch();
    }

    @Override
    public Long countAllByProductReactIdAndStatus(Long productReactId, ProductReviewStatus... statuses) {
        return queryFactory.select(productReview.count())
                .from(productReview)
                .where(equalMemberReactId(productReactId), inReviewStatus(statuses))
                .fetchOne();
    }

    @Override
    public List<ProductReviewDto> findAllByMemberId(Long memberId, PageOption pageOption) {
        return queryFactory.select(ProductReviewDto.asDto())
                .from(productReview)
                .where(equalMemberId(memberId))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(productReview.createdAt.desc())
                .fetch();
    }

    @Override
    public Long countAllByMemberId(Long memberId) {
        return queryFactory.select(productReview.count())
                .from(productReview)
                .where(equalMemberId(memberId))
                .fetchOne();
    }

    private BooleanExpression equalMemberReactId(Long productReactId) {
        if (productReactId == null) {
            throw new IllegalArgumentException("productReact를 입력해주세요.");
        }
        return productReview.productReact.id.eq(productReactId);
    }

    private BooleanExpression inReviewStatus(ProductReviewStatus... statuses) {
        if (statuses == null || statuses.length == 0) {
            throw new IllegalArgumentException("status를 입력해주세요.");
        }
        return productReview.status.in(statuses);
    }

    private BooleanExpression equalMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId를 입력해주세요.");
        }
        return productReview.memberId.eq(memberId);
    }
}
