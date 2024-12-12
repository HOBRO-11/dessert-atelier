package com.yangnjo.dessert_atelier.repository.auth.query.query_dsl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.domain_model.auth.QRefreshToken;
import com.yangnjo.dessert_atelier.repository.auth.dto.RefreshTokenDto;
import com.yangnjo.dessert_atelier.repository.auth.query.RefreshTokenQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenQueryRepoImpl implements RefreshTokenQueryRepo {

    private final JPAQueryFactory queryFactory;
    QRefreshToken refreshToken = QRefreshToken.refreshToken;

    @Override
    public Optional<RefreshTokenDto> findByMemberId(Long memberId) {
        return Optional.ofNullable(
                queryFactory
                        .select(RefreshTokenDto.asDto())
                        .from(refreshToken)
                        .where(equalMemberId(memberId))
                        .fetchOne());
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return refreshToken.member.id.eq(memberId);
    }

}
