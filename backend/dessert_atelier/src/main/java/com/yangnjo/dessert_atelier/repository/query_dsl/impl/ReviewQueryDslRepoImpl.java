package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QReviewImages.*;
import static com.yangnjo.dessert_atelier.domain.QReviews.*;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.ReviewOrigin;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.ReviewDisplayDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.ReviewQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReviewQueryDslRepoImpl implements ReviewQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewDisplayDto> searchWithCondition(PageOption pageOption, Long userId, Long dpId, ReviewOrigin origin) {
        return queryFactory
                .select(constructor(ReviewDisplayDto.class, reviews.id, reviews.users.id, reviews.comment,
                        reviews.reviewImages.imagesUrl, reviews.createdAt, reviews.updatedAt, reviews.createdAt,
                        reviews.updatedAt,
                        reviews.origin))
                .join(reviews.reviewImages, reviewImages)
                .where(condition(userId, dpId, origin))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
    public Long countWithCondition(Long userId, Long dpId, ReviewOrigin origin) {
        return queryFactory
                .select(reviews.count())
                .join(reviews.reviewImages, reviewImages)
                .where(condition(userId, dpId, origin))
                .fetchOne();
    }

    private OrderSpecifier<Long> sort(Direction direction) {
        if (direction.isAscending()) {
            return reviews.id.asc();
        }
        return reviews.id.desc();
    }

    private BooleanExpression condition(Long userId, Long dpId, ReviewOrigin origin) {
        BooleanExpression ex = null;
        if (userId != null) {
            ex = isUser(userId);
            if (dpId != null) {
                return ex.and(isProduct(dpId));
            } else {
                return ex;
            }
        }

        if (dpId != null) {
            ex = isProduct(dpId);
            if (origin != null) {
                return ex.and(isOrigin(origin));
            } else {
                return ex;
            }
        }

        return isOrigin(origin);
    }

    private BooleanExpression isOrigin(ReviewOrigin origin) {
        return origin == null ? null : reviews.origin.eq(origin);
    }

    private BooleanExpression isProduct(Long dpId) {
        return dpId == null ? null : reviews.displayProducts.id.eq(dpId);
    }

    private BooleanExpression isUser(Long userId) {
        return userId == null ? null : reviews.users.id.eq(userId);
    }

}
