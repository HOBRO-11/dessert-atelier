package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yangnjo.dessert_atelier.common.dto.qna.QnADto;
import com.yangnjo.dessert_atelier.common.dto.qna.QnAUnAnswerDto;
import com.yangnjo.dessert_atelier.db.model.QnAStatus;

public interface QnAQueryRepo {

    List<QnAUnAnswerDto> findByUnAnswer(int page, int size, Direction direction);

    Long countFindByUnAnswer(Direction direction);

    List<QnADto> findByStatus(int page, int size, QnAStatus status, Direction direction);

    Long countFindByStatus(QnAStatus status, Direction direction);

}