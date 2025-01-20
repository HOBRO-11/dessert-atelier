package com.yangnjo.dessert_atelier.auth.query;

import java.util.Optional;

import com.yangnjo.dessert_atelier.auth.dto.RefreshTokenDto;

public interface RefreshTokenQueryService {
    
    Optional<RefreshTokenDto> findByMemberId(Long memberId);
}
