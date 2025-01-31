package com.yangnjo.dessert_atelier.auth.dto;

import java.time.LocalDateTime;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.yangnjo.dessert_atelier.auth.domain.entity.QRefreshToken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenDto {
    private Long id;
    private Long memberId;
    private String refreshTokenSignature;
    private LocalDateTime expiredDate;

    public static Expression<RefreshTokenDto> asDto() {
        QRefreshToken refreshToken = QRefreshToken.refreshToken;
        return Projections.constructor(RefreshTokenDto.class,
                refreshToken.id,
                refreshToken.memberId,
                refreshToken.refreshTokenSignature,
                refreshToken.expiredDate);
    }
}
