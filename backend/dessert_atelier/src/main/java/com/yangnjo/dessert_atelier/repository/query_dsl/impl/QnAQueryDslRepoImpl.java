package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QQnAs.*;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.QnAStatus;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.QnAFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.QnAUnAnswerDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.QnAQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QnAQueryDslRepoImpl implements QnAQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QnAUnAnswerDto> searchWithStatusWaiting(PageOption pageOption) {
        return queryFactory
                .select(constructor(QnAUnAnswerDto.class, qnAs.id, qnAs.displayProducts.id, qnAs.users.id, qnAs.comment,
                        qnAs.commentUpdatedAt))
                .from(qnAs)
                .where(isStatus(QnAStatus.WAITING))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
    public Long countWithStatusWaiting(Direction direction) {
        return queryFactory
                .select(qnAs.count())
                .from(qnAs)
                .where(isStatus(QnAStatus.WAITING))
                .orderBy(sort(direction))
                .fetchOne();
    }

    @Override
    public List<QnAFlatDto> searchWithStatus(PageOption pageOption, QnAStatus status) {
        return queryFactory
                .select(constructor(QnAFlatDto.class, qnAs.id, qnAs.displayProducts.id, qnAs.users.id, qnAs.comment,
                        qnAs.commentUpdatedAt, qnAs.status, qnAs.answer, qnAs.answerUpdatedAt))
                .from(qnAs)
                .where(isStatus(status))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
    public Long countWithStatus(QnAStatus status, Direction direction) {
        return queryFactory
                .select(qnAs.count())
                .from(qnAs)
                .where(isStatus(status))
                .orderBy(sort(direction))
                .fetchOne();
    }

    private BooleanExpression isStatus(QnAStatus status) {
        if (status == null) {
            return null;
        }
        return qnAs.status.eq(status);
    }

    private OrderSpecifier<Long> sort(Direction direction) {
        if (direction == null) {
            return qnAs.id.desc();
        }
        if (direction.isAscending()) {
            return qnAs.id.asc();
        }
        return qnAs.id.desc();
    }
}
