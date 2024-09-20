package com.yangnjo.dessert_atelier.db.repository.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QAddress.address;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.address.AddressDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.repository.AddressQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressQueryDslRepoImpl implements AddressQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AddressDto> findDefaultAddress(Users user) {
        return Optional.of(
                queryFactory
                        .select(constructor(AddressDto.class, address.id, address.naming, address.postCode,
                                address.detailAddress,
                                address.receiver, address.phone, address.isDefault))
                        .from(address)
                        .where(isDefaultAddress().and(isUsers(user)))
                        .fetchOne());
    }

    private BooleanExpression isDefaultAddress() {
        return address.isDefault.isTrue();
    }

    private BooleanExpression isUsers(Users user) {
        if (user == null) {
            // TODO User 값은 반드시 들어가야함
            throw new RuntimeException();
        }

        return address.users.eq(user);
    }

}
