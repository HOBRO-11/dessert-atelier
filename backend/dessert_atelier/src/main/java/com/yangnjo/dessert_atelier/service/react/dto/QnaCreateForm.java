package com.yangnjo.dessert_atelier.service.react.dto;

import com.yangnjo.dessert_atelier.domain_service.react.dto.QnaCreateDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QnaCreateForm {
    Long dpId;
    String password;
    Long memberId;
    String comment;

    public QnaCreateDto toDto(){
        return new QnaCreateDto(dpId, password, memberId, comment);
    }
}
