package com.yangnjo.dessert_atelier.react.domain.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.commons.util.page.PageOption;
import com.yangnjo.dessert_atelier.commons.util.page.PeriodOption;
import com.yangnjo.dessert_atelier.react.domain.entity.ProductQnAStatus;
import com.yangnjo.dessert_atelier.react.dto.ProductQnADto;

public interface ProductQnaQueryService {

    List<ProductQnADto> findAllByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

    List<ProductQnADto> findAllByProductReactIdAndStatus(Long productReactId, PageOption pageOption, ProductQnAStatus... statuses);

    Long countAllByMemberId(Long memberId);

    Long countAllByProductReactIdAndStatus(Long productReactId, ProductQnAStatus... statuses);

}