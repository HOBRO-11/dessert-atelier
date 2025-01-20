package com.yangnjo.dessert_atelier.auth.query.query_dsl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.auth.domain.entity.QRefreshToken;
import com.yangnjo.dessert_atelier.auth.dto.RefreshTokenDto;
import com.yangnjo.dessert_atelier.auth.query.RefreshTokenQueryService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenQueryServiceV1 implements RefreshTokenQueryService {

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
        return refreshToken.memberId.eq(memberId);
    }

}
