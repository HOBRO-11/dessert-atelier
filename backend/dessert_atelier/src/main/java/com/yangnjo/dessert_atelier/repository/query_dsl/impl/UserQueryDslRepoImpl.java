package com.yangnjo.dessert_atelier.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.*;
import static com.yangnjo.dessert_atelier.domain.QUsers.*;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain.UserStatus;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.UserFlatDto;
import com.yangnjo.dessert_atelier.repository.query_dsl.UserQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryDslRepoImpl implements UserQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserFlatDto> searchWithStatus(PageOption pageOption, UserStatus userStatus) {
        return queryFactory
                .select(constructor(UserFlatDto.class, users.id, users.email, users.name, users.phone,
                        users.userStatus))
                .from(users)
                .where(isUserStatus(userStatus))
                .offset(pageOption.getPage() * pageOption.getSize())
                .limit(pageOption.getSize())
                .orderBy(sort(pageOption.getDirection()))
                .fetch();
    }

    @Override
    public Long countWithStatus(UserStatus userStatus) {
        return queryFactory
                .select(users.count())
                .from(users)
                .where(isUserStatus(userStatus))
                .fetchOne();
    }

    private BooleanExpression isUserStatus(UserStatus userStatus) {
        return userStatus == null ? null : users.userStatus.eq(userStatus);
    }

    private OrderSpecifier<Long> sort(Direction direction) {
        if (direction == null) {
            return users.id.desc();
        }
        if (direction.isAscending()) {
            return users.id.asc();
        }
        return users.id.desc();
    }

}
