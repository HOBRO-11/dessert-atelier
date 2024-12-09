package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;
import static com.yangnjo.dessert_atelier.domain.react.QQnA.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.dto.QnADto;
import com.yangnjo.dessert_atelier.repository.query.QnAQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QnAQueryRepoImpl implements QnAQueryRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public QnADto findById(Long id) {
        return queryFactory.select(QnADto.asDto())
                .from(qnA)
                .where(equalQnAId(id))
                .fetchOne();
    }

    @Override
    public List<QnADto> findAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        return queryFactory.select(QnADto.asDto())
                .from(qnA)
                .leftJoin(member).on(qnA.member.id.eq(member.id))
                .where(equalDpId(dpId), equalStatus(status))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(qnA.id))
                .fetch();
    }

    @Override
    public List<QnADto> findAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        return queryFactory.select(QnADto.asDto())
                .from(qnA)
                .leftJoin(member).on(qnA.member.id.eq(member.id))
                .where(equalDpId(dpId), notEqualStatus(status))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(qnA.id))
                .fetch();
    }

    @Override
    public Long countByDpIdAndStatus(Long dpId, QnAStatus status) {
        return queryFactory.select(qnA.count())
                .from(qnA)
                .where(equalDpId(dpId), equalStatus(status))
                .fetchOne();
    }

    @Override
    public Long countByDpIdAndExceptStatus(Long dpId, QnAStatus status) {
        return queryFactory.select(qnA.count())
                .from(qnA)
                .where(equalDpId(dpId), notEqualStatus(status))
                .fetchOne();
    }

    private BooleanExpression equalDpId(Long dpId) {
        return qnA.displayProduct.id.eq(dpId);
    }

    private BooleanExpression equalQnAId(Long id) {
        return qnA.id.eq(id);
    }

    private BooleanExpression notEqualStatus(QnAStatus status) {
        return qnA.qnaStatus.ne(status);
    }

    private BooleanExpression equalStatus(QnAStatus status) {
        return status == null ? null : qnA.qnaStatus.eq(status);
    }
}
