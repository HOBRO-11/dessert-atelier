package com.yangnjo.dessert_atelier.repository.member.query.query_dsl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain_model.member.QAddress;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressDto;
import com.yangnjo.dessert_atelier.repository.member.dto.AddressSimpleDto;
import com.yangnjo.dessert_atelier.repository.member.query.AddressQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressQueryRepoImpl implements AddressQueryRepo {

    private final JPAQueryFactory queryFactory;
    QAddress address = QAddress.address;

    @Override
    public List<AddressDto> findAllByMemberId(Long memberId) {
        return queryFactory
                .select(AddressDto.asDto())
                .from(address)
                .where(equalMemberId(memberId))
                .fetch();
    }

    @Override
    public List<AddressSimpleDto> findAllSimpleByMemberId(Long memberId) {
        return queryFactory
                .select(AddressSimpleDto.asDto())
                .from(address)
                .where(equalMemberId(memberId))
                .fetch();
    }

    @Override
    public Optional<AddressDto> findDefaultAddressByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .select(AddressDto.asDto())
                        .from(address)
                        .where(equalMemberId(memberId), isDefault(true))
                        .fetchOne());
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return memberId == null ? null : address.member.id.eq(memberId);
    }

    private BooleanExpression isDefault(Boolean isDefault) {
        return isDefault == null ? null : address.isDefault.eq(isDefault);
    }
}
