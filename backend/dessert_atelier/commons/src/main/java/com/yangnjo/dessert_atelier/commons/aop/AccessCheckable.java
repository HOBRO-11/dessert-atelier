package com.yangnjo.dessert_atelier.commons.aop;

import org.springframework.security.access.AccessDeniedException;

public interface AccessCheckable {
    
    void check(Long memberId, Long entityId) throws AccessDeniedException;
}
