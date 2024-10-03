package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.ReviewOrigin;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.ReviewDisplayDto;

public interface ReviewQueryDslRepo {

    List<ReviewDisplayDto> searchWithCondition(PageOption pageOption, Long userId, Long productId, ReviewOrigin origin);

    Long countWithCondition(Long userId, Long productId, ReviewOrigin origin);

}