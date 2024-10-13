package com.yangnjo.dessert_atelier.repository.query;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.dto.MemberSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepoImpl {

  private final JPAQueryFactory queryFactory;

  public MemberDto findByMemberStatusAndMemberOrigin(MemberStatus memberStatus, MemberOrigin memberOrigin,
      PageOption pageOption) {
    return queryFactory.select(MemberDto.asDto())
        .from(member)
        .where(equalMemberStatus(memberStatus), equalMemberOrigin(memberOrigin))
        .offset(pageOption.getOffset())
        .limit(pageOption.getSize())
        .orderBy(pageOption.getDirection(member.id))
        .fetchOne();
  }

  public List<MemberSimpleDto> findSimplesByMemberStatusAndMemberOrigin(MemberStatus memberStatus,
      MemberOrigin memberOrigin, PageOption pageOption) {
    return queryFactory.select(MemberSimpleDto.asDto())
        .from(member)
        .where(equalMemberStatus(memberStatus), equalMemberOrigin(memberOrigin))
        .offset(pageOption.getOffset())
        .limit(pageOption.getSize())
        .orderBy(pageOption.getDirection(member.id))
        .fetch();
  }

  public Long countSimplesByMemberStatusAndMemberOrigin(MemberStatus memberStatus, MemberOrigin memberOrigin) {
    return queryFactory.select(member.count())
        .from(member)
        .where(equalMemberStatus(memberStatus), equalMemberOrigin(memberOrigin))
        .fetchOne();
  }

  private BooleanExpression equalMemberStatus(MemberStatus memberStatus) {
    return memberStatus != null ? member.memberStatus.eq(memberStatus) : null;
  }

  private BooleanExpression equalMemberOrigin(MemberOrigin memberOrigin) {
    return memberOrigin != null ? member.memberOrigin.eq(memberOrigin) : null;
  }
}
