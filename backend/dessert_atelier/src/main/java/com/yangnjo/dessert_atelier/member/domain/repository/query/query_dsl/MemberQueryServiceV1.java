package com.yangnjo.dessert_atelier.member.domain.repository.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.member.domain.entity.MemberStatus;
import com.yangnjo.dessert_atelier.member.domain.entity.QMember;
import com.yangnjo.dessert_atelier.member.domain.repository.query.MemberQuerService;
import com.yangnjo.dessert_atelier.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.member.dto.MemberSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryServiceV1 implements MemberQuerService {

    private final JPAQueryFactory queryFactory;
    QMember member = QMember.member;

    @Override
    public List<MemberSimpleDto> findSimplesByMemberStatus(MemberStatus memberStatus, PageOption pageOption) {
        return queryFactory.select(MemberSimpleDto.asDto())
                .from(member)
                .where(equalMemberStatus(memberStatus))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(member.id))
                .fetch();
    }

    @Override
    public Optional<MemberSimpleDto> findSimpleById(Long id) {
        return Optional.ofNullable(queryFactory.select(MemberSimpleDto.asDto())
                .from(member)
                .where(equalMemberId(id))
                .fetchOne());
    }

    @Override
    public Optional<MemberDto> findById(Long id) {
        return Optional.ofNullable(queryFactory.select(MemberDto.asDto())
                .from(member)
                .where(equalMemberId(id))
                .fetchOne());
    }

    @Override
    public Optional<MemberDto> findByEmail(String email) {
        return Optional.ofNullable(queryFactory.select(MemberDto.asDto())
                .from(member)
                .where(equalMemberEmail(email))
                .fetchOne());
    }

    @Override
    public Long countSimplesByMemberStatus(MemberStatus memberStatus) {
        return queryFactory.select(member.count())
                .from(member)
                .where(equalMemberStatus(memberStatus))
                .fetchOne();
    }

    private BooleanExpression equalMemberEmail(String email) {
        return member.email.eq(email);
    }

    private BooleanExpression equalMemberId(Long id) {
        return member.id.eq(id);
    }

    private BooleanExpression equalMemberStatus(MemberStatus memberStatus) {
        return memberStatus != null ? member.status.eq(memberStatus) : null;
    }
}
