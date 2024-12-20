package com.yangnjo.dessert_atelier.repository.react.query.query_dsl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.QMember;
import com.yangnjo.dessert_atelier.domain_model.react.QReview;
import com.yangnjo.dessert_atelier.domain_model.react.ReviewStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.ReviewDto;
import com.yangnjo.dessert_atelier.repository.react.query.ReviewQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepoImpl implements ReviewQueryRepo {

    private final JPAQueryFactory queryFactory;
    QReview review = QReview.review;
    QMember member = QMember.member;

    @Override
    public List<ReviewDto> findAllByDppIdAndStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
        List<ReviewDto> dtos = queryFactory.select(ReviewDto.asDto())
                .from(review)
                .where(equalDpId(dpId), equalReviewStatus(status))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(review.createdAt.desc())
                .fetch();

        setMemberNameAtDtos(dtos);

        return dtos;
    }

    @Override
    public Long countAllByDpIdAndStatus(Long dpId, ReviewStatus status) {
        return queryFactory.select(review.count())
                .from(review)
                .where(equalDpId(dpId), equalReviewStatus(status))
                .fetchOne();
    }

    @Override
    public List<ReviewDto> findAllByMemberId(Long memberId, PageOption pageOption) {
        List<ReviewDto> dtos = queryFactory.select(ReviewDto.asDto())
                .from(review)
                .where(equalMemberId(memberId))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(review.createdAt.desc())
                .fetch();

        setMemberNameAtDtos(dtos);

        return dtos;
    }

    @Override
    public Long countAllByMemberId(Long memberId) {
        return queryFactory.select(review.count())
                .from(review)
                .where(equalMemberId(memberId))
                .fetchOne();
    }

    @Override
    public List<ReviewDto> findAllByDppIdAndExceptStatus(Long dpId, ReviewStatus status, PageOption pageOption) {
        List<ReviewDto> dtos = queryFactory.select(ReviewDto.asDto())
                .from(review)
                .where(equalDpId(dpId), exceptReviewStatus(status))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(review.createdAt.desc())
                .fetch();

        setMemberNameAtDtos(dtos);

        return dtos;
    }

    @Override
    public Long countAllByDpIdAndExceptStatus(Long dpId, ReviewStatus status) {
        return queryFactory.select(review.count())
                .from(review)
                .where(equalDpId(dpId), exceptReviewStatus(status))
                .fetchOne();
    }

    private void setMemberNameAtDtos(List<ReviewDto> dtos) {
        Set<Long> memberIds = dtos.stream().map(ReviewDto::getMemberId).filter(id -> id != null).collect(Collectors.toSet());

        Map<Long, String> memberIdAndName = null;

        if (memberIds != null && (memberIds.isEmpty() == false)) {
            memberIdAndName = queryFactory.select(member.id, member.name)
                    .from(member)
                    .where(member.id.in(memberIds))
                    .fetch()
                    .stream()
                    .collect(Collectors.toMap(tuple -> tuple.get(member.id),
                            tuple -> tuple.get(member.name)));
        }

        for (ReviewDto dto : dtos) {
            String name = null;

            if (memberIdAndName != null) {
                name = memberIdAndName.get(dto.getMemberId());
            }

            if (name == null) {
                name = "GUEST";
            }
            dto.setName(name);
        }
    }

    private BooleanExpression exceptReviewStatus(ReviewStatus status) {
        return status == null ? null : review.reviewStatus.ne(status);
    }

    private BooleanExpression equalDpId(Long dpId) {
        return dpId == null ? null : review.displayProduct.id.eq(dpId);
    }

    private BooleanExpression equalReviewStatus(ReviewStatus status) {
        return status == null ? null : review.reviewStatus.eq(status);
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return memberId == null ? null : review.member.id.eq(memberId);
    }
}
