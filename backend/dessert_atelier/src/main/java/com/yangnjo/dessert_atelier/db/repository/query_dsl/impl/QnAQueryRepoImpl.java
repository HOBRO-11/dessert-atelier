package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QQnAs.qnAs;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.qna.QnADto;
import com.yangnjo.dessert_atelier.common.dto.qna.QnAUnAnswerDto;
import com.yangnjo.dessert_atelier.db.model.QnAStatus;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.QnAQueryRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QnAQueryRepoImpl implements QnAQueryRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<QnAUnAnswerDto> findByUnAnswer(int page, int size, Direction direction) {
        return queryFactory
                .select(constructor(QnAUnAnswerDto.class, qnAs.id, qnAs.users, qnAs.comment, qnAs.commentUpdatedAt))
                .from(qnAs)
                .where(isStatus(QnAStatus.WAITING))
                .offset(page * size)
                .limit(size)
                .orderBy(sort(direction))
                .fetch();
    }

    @Override
    public Long countFindByUnAnswer(Direction direction) {
        return queryFactory
                .select(qnAs.count())
                .from(qnAs)
                .where(isStatus(QnAStatus.WAITING))
                .orderBy(sort(direction))
                .fetchOne();
    }

    @Override
    public List<QnADto> findByStatus(int page, int size, QnAStatus status, Direction direction) {
        return queryFactory
                .select(constructor(QnADto.class, qnAs.id, qnAs.users, qnAs.comment, qnAs.commentUpdatedAt, qnAs.answer,
                        qnAs.answerUpdatedAt, qnAs.status))
                .from(qnAs)
                .where(isStatus(status))
                .offset(page * size)
                .limit(size)
                .orderBy(sort(direction))
                .fetch();
    }

    @Override
    public Long countFindByStatus(QnAStatus status, Direction direction) {
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
        if (direction.isAscending()) {
            return qnAs.id.asc();
        }
        return qnAs.id.desc();
    }
}
