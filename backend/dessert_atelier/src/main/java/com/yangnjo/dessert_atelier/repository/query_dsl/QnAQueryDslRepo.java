package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yangnjo.dessert_atelier.domain.QnAStatus;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.QnAFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.QnAUnAnswerDto;

public interface QnAQueryDslRepo {

    List<QnAUnAnswerDto> searchWithStatusWaiting(PageOption pageOption);

    Long countWithStatusWaiting(Direction direction);

    List<QnAFlatDto> searchWithStatus(PageOption pageOption, QnAStatus status);

    Long countWithStatus(QnAStatus status, Direction direction);

}