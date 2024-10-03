package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QAddresses.*;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.repository.dto.AddressFlatDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.AddressQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressQueryDslRepoImpl implements AddressQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
	public List<AddressFlatDto> searchWithCondition(Long userId) {
        return queryFactory
                .select(constructor(AddressFlatDto.class, addresses.id, addresses.naming,
                        addresses.destination.postCode, addresses.destination.detailAddress,
                        addresses.destination.receiver, addresses.destination.phone, addresses.isDefault))
                .from(addresses)
                .where(isUser(userId))
                .fetch();
    }

    private BooleanExpression isUser(Long userId) {
        return addresses.users.id.eq(userId);
    }

}
