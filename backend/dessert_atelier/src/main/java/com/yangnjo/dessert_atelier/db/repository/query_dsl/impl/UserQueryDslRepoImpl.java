package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QUsers.users;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.db.model.UserStatus;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.UserQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryDslRepoImpl implements UserQueryDslRepo  {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserDto> findByStatus(int page, int size, UserStatus userStatus, Direction direction) {
        return queryFactory
                .select(constructor(UserDto.class, users.id, users.email, users.name))
                .from(users)
                .where(isUserStatus(userStatus))
                .offset(page * size)
                .limit(size)
                .orderBy(sort(direction))
                .fetch();
    }

    @Override
    public Long count(UserStatus userStatus) {
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
        if (direction.isAscending()) {
            return users.id.asc();
        }
        return users.id.desc();
    }

}
