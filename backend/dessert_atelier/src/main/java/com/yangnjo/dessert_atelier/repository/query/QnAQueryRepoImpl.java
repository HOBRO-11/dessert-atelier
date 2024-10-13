package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;
import static com.yangnjo.dessert_atelier.domain.react.QQnA.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.QnADto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QnAQueryRepoImpl {

  private final JPAQueryFactory queryFactory;

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

  public Long countByDpIdAndStatus(Long dpId, QnAStatus status) {
    return queryFactory.select(qnA.count())
        .from(qnA)
        .where(equalDpId(dpId), equalStatus(status))
        .fetchOne();
  }

  private BooleanExpression equalDpId(Long dpId) {
    return qnA.displayProduct.id.eq(dpId);
  }

  private BooleanExpression equalStatus(QnAStatus status) {
    return status == null ? null : qnA.qnaStatus.eq(status);
  }
}
