package com.yangnjo.dessert_atelier.member.domain.repository.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.member.domain.entity.QMemberAddress;
import com.yangnjo.dessert_atelier.member.domain.repository.query.MemberAddressQueryService;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressDto;
import com.yangnjo.dessert_atelier.member.dto.MemberAddressSimpleDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberAddressQueryServiceV1 implements MemberAddressQueryService {

    private final JPAQueryFactory queryFactory;
    QMemberAddress memberAddress = QMemberAddress.memberAddress;

    @Override
    public List<MemberAddressDto> findAllByMemberId(Long memberId) {
        return queryFactory
                .select(MemberAddressDto.asDto())
                .from(memberAddress)
                .where(equalMemberId(memberId))
                .fetch();
    }

    @Override
    public List<MemberAddressSimpleDto> findAllSimpleByMemberId(Long memberId) {
        return queryFactory
                .select(MemberAddressSimpleDto.asDto())
                .from(memberAddress)
                .where(equalMemberId(memberId))
                .fetch();
    }

    @Override
    public Optional<MemberAddressDto> findDefaultAddressByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .select(MemberAddressDto.asDto())
                        .from(memberAddress)
                        .where(equalMemberId(memberId), isDefault(true))
                        .fetchOne());
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return memberId == null ? null : memberAddress.member.id.eq(memberId);
    }

    private BooleanExpression isDefault(Boolean isDefault) {
        return isDefault == null ? null : memberAddress.isDefault.eq(isDefault);
    }
}
