package com.yangnjo.dessert_atelier.repository.react.query.query_dsl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.member.QMember;
import com.yangnjo.dessert_atelier.domain_model.react.QQnA;
import com.yangnjo.dessert_atelier.domain_model.react.QnAStatus;
import com.yangnjo.dessert_atelier.repository.react.dto.QnADto;
import com.yangnjo.dessert_atelier.repository.react.query.QnAQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QnAQueryRepoImpl implements QnAQueryRepo {

    private final JPAQueryFactory queryFactory;
    QQnA qnA = QQnA.qnA;
    QMember member = QMember.member;

    @Override
    public List<QnADto> findAllByDpIdAndStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        List<QnADto> dtos = queryFactory.select(QnADto.asDto())
                .from(qnA)
                .where(equalDpId(dpId), equalStatus(status))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(qnA.id))
                .fetch();

        setMemberNameAtDtos(dtos);

        return dtos;

    }

    @Override
    public List<QnADto> findAllByDpIdAndExceptStatus(Long dpId, QnAStatus status, PageOption pageOption) {
        List<QnADto> dtos = queryFactory.select(QnADto.asDto())
                .from(qnA)
                .where(equalDpId(dpId), notEqualStatus(status))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(qnA.id))
                .fetch();

        setMemberNameAtDtos(dtos);

        return dtos;
    }

    @Override
    public List<QnADto> findAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption) {
        List<QnADto> dtos = queryFactory.select(QnADto.asDto())
                .from(qnA)
                .where(equalMemberId(memberId), PeriodOption.betweenLDT(qnA.commentCreatedAt, periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(qnA.id))
                .fetch();

        setMemberNameAtDtos(dtos);

        return dtos;
    }

    @Override
    public Long countAllByMemberId(Long memberId) {
        return queryFactory.select(qnA.count())
                .from(qnA)
                .where(equalMemberId(memberId))
                .fetchOne();
    }

    @Override
    public Long countAllByDpIdAndStatus(Long dpId, QnAStatus status) {
        return queryFactory.select(qnA.count())
                .from(qnA)
                .where(equalDpId(dpId), equalStatus(status))
                .fetchOne();
    }

    @Override
    public Long countAllByDpIdAndExceptStatus(Long dpId, QnAStatus status) {
        return queryFactory.select(qnA.count())
                .from(qnA)
                .where(equalDpId(dpId), notEqualStatus(status))
                .fetchOne();
    }

    private void setMemberNameAtDtos(List<QnADto> dtos) {
        Set<Long> memberIds = dtos.stream().map(QnADto::getMemberId).collect(Collectors.toSet());

        Map<Long, String> memberIdAndName = queryFactory.select(member.id, member.name)
                .from(member)
                .where(member.id.in(memberIds))
                .fetch()
                .stream()
                .collect(Collectors.toMap(tuple -> tuple.get(member.id),
                        tuple -> tuple.get(member.name)));

        dtos.stream().forEach(dto -> {
            String name = memberIdAndName.get(dto.getMemberId());
            if(name == null) {
                name = "GUEST";
            }
            dto.setName(name);
        });
    }

    private BooleanExpression equalDpId(Long dpId) {
        return qnA.displayProduct.id.eq(dpId);
    }

    private BooleanExpression notEqualStatus(QnAStatus status) {
        return qnA.qnaStatus.ne(status);
    }

    private BooleanExpression equalStatus(QnAStatus status) {
        return status == null ? null : qnA.qnaStatus.eq(status);
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return qnA.member.id.eq(memberId);
    }
}
