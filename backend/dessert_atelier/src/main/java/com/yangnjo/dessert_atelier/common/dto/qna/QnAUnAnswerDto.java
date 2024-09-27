package com.yangnjo.dessert_atelier.common.dto.qna;

import java.time.LocalDateTime;

import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QnAUnAnswerDto {

    public Long id;

    public Users users;

    public String comment ;

    public LocalDateTime commentUpdatedAt;

}
