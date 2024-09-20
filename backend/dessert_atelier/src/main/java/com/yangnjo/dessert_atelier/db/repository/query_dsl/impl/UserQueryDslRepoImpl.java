package com.yangnjo.dessert_atelier.db.repository.query_dsl.impl;

import static com.querydsl.core.types.Projections.constructor;
import static com.yangnjo.dessert_atelier.db.entity.QUsers.users;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.UserStatus;
import com.yangnjo.dessert_atelier.db.repository.query_dsl.UserQueryDslRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryDslRepoImpl implements UserQueryDslRepo {

    private final JPAQueryFactory queryFactory;

    @Deprecated
    @Override
    public boolean modify(Users user, String name, String password, Integer phone) {
        JPAUpdateClause clause = queryFactory.update(users).where(users.eq(user));

        if (password != null && !password.isEmpty()) {
            clause.set(users.password, password);
        }

        if (name != null && !name.isEmpty()) {
            clause.set(users.name, name);
        }

        if (phone != null) {
            clause.set(users.phone, phone);
        }

        long affectedRows = clause.execute();

        return affectedRows == 1;
    }

    @Override
    public List<UserDto> search(int page, int size, UserStatus userStatus) {
        return queryFactory
                .select(constructor(UserDto.class, users.id, users.email, users.name))
                .from(users)
                .where(isUserStatus(userStatus))
                .offset(page * size)
                .limit(size)
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

}
