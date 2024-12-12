package com.yangnjo.dessert_atelier.repository.auth.query;

import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.auth.dto.RefreshTokenDto;

public interface RefreshTokenQueryRepo {
    
    Optional<RefreshTokenDto> findByMemberId(Long memberId);
}
