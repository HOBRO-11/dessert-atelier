package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yangnjo.dessert_atelier.common.dto.review.ReviewDto;
import com.yangnjo.dessert_atelier.db.model.ReviewOrigin;

public interface ReviewQueryDslRepo {

    List<ReviewDto> findByCondition(int page, int size, Long userId, Long productId, ReviewOrigin origin,
            Direction direction);

    Long countFindByCondition(Long userId, Long productId, ReviewOrigin origin);

}