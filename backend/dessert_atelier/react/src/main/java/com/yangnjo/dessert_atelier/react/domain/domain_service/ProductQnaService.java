package com.yangnjo.dessert_atelier.react.domain.domain_service;

import com.yangnjo.dessert_atelier.react.domain.entity.ProductQnAStatus;
import com.yangnjo.dessert_atelier.react.dto.ProductQnaCreateDto;

public interface ProductQnaService {

    Long createByMember(ProductQnaCreateDto dto);

    Long createByGuest(ProductQnaCreateDto dto);

    void answer(Long qnaId, String answer);

    void removeAnswer(Long memberId, Long qnaId);

    void updateStatus(Long memberId, Long qnaId, ProductQnAStatus status);

    void delete(Long qnaId);

}