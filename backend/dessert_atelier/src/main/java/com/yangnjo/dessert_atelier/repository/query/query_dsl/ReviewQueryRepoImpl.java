package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;
import static com.yangnjo.dessert_atelier.domain.react.QReview.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.dto.ReviewDto;
import com.yangnjo.dessert_atelier.repository.query.ReviewQueryRepo;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ReviewQueryRepoImpl implements ReviewQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<ReviewDto> findAllByDppIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
    return queryFactory.select(ReviewDto.asIncompleteDto())
        .from(review)
        .leftJoin(member).on(review.member.id.eq(member.id))
        .where(equalDpId(dpId), equalReviewStatus(status))
        .offset(pageOption.getOffset())
        .limit(pageOption.getSize())
        .orderBy(review.createdAt.desc())
        .fetch(); 
  }

  @Override
  public Long countAllByDpIdAndStatus(Long dpId, ReviewStatus status) {
    return queryFactory.select(review.count())
        .from(review)
        .where(equalDpId(dpId), equalReviewStatus(status))
        .fetchOne();
  }

  private BooleanExpression equalDpId(Long dpId) {
    return dpId == null ? null : review.displayProduct.id.eq(dpId);
  }

  private BooleanExpression equalReviewStatus(ReviewStatus status) {
    return status == null ? null : review.reviewStatus.eq(status);
  }
}
