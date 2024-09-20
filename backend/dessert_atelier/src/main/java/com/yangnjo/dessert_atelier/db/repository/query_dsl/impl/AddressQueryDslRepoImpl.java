package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QAddresses.addresses;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.address.AddressDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.AddressQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressQueryDslRepoImpl implements AddressQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AddressDto> findDefaultAddress(Users user) {
        return Optional.of(
                queryFactory
                        .select(constructor(AddressDto.class, addresses.id, addresses.naming, addresses.postCode,
                                addresses.detailAddress,
                                addresses.receiver, addresses.phone, addresses.isDefault))
                        .from(addresses)
                        .where(isDefaultAddress().and(isUsers(user)))
                        .fetchOne());
    }

    private BooleanExpression isDefaultAddress() {
        return addresses.isDefault.isTrue();
    }

    private BooleanExpression isUsers(Users user) {
        if (user == null) {
            // TODO User 값은 반드시 들어가야함
            throw new RuntimeException();
        }

        return addresses.users.eq(user);
    }

}
