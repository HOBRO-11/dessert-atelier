package com.yangnjo.dessert_atelier.repository.query.query_dsl;

import static com.yangnjo.dessert_atelier.domain.member.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.member.MemberStatus;
import com.yangnjo.dessert_atelier.repository.dto.MemberDto;
import com.yangnjo.dessert_atelier.repository.dto.MemberSimpleDto;
import com.yangnjo.dessert_atelier.repository.query.MemberQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepoImpl implements MemberQueryRepo {

  private final JPAQueryFactory queryFactory;

  @Override
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

	@Override
	public MemberSimpleDto findMemberById(Long id) {
		return queryFactory.select(MemberSimpleDto.asDto())
				.from(member)
				.where(equalMemberId(id))
				.fetchOne();
	}

  @Override
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

  @Override
  public Long countSimplesByMemberStatusAndMemberOrigin(MemberStatus memberStatus, MemberOrigin memberOrigin) {
		return queryFactory.select(member.count())
        .from(member)
        .where(equalMemberStatus(memberStatus), equalMemberOrigin(memberOrigin))
        .fetchOne();
	}

    @Override
    public MemberDto findByEmail(String email) {
        return queryFactory.select(MemberDto.asDto())
        .from(member)
        .where(equalMemberEmail(email))
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

  private BooleanExpression equalMemberOrigin(MemberOrigin memberOrigin) {
    return memberOrigin != null ? member.memberOrigin.eq(memberOrigin) : null;
  }

}
