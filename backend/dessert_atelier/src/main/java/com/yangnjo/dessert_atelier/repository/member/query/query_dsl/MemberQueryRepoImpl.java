package com.yangnjo.dessert_atelier.repository.member.query.query_dsl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain_model.member.MemberStatus;
import com.yangnjo.dessert_atelier.domain_model.member.QMember;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.member.dto.MemberSimpleDto;
import com.yangnjo.dessert_atelier.repository.member.query.MemberQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepoImpl implements MemberQueryRepo {

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
    public MemberSimpleDto findSimpleById(Long id) {
        return queryFactory.select(MemberSimpleDto.asDto())
                .from(member)
                .where(equalMemberId(id))
                .fetchOne();
    }

    @Override
    public MemberDto findById(Long id) {
        return queryFactory.select(MemberDto.asDto())
                .from(member)
                .where(equalMemberId(id))
                .fetchOne();
    }

    @Override
    public MemberDto findByEmail(String email) {
        return queryFactory.select(MemberDto.asDto())
                .from(member)
                .where(equalMemberEmail(email))
                .fetchOne();
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
        return memberStatus != null ? member.memberStatus.eq(memberStatus) : null;
    }
}
