package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.OptionCreateDto;

//FIXME 옵션 생성 방법에 대해 생각해보기 PQ를 전혀 고려하고 있지 않음
public interface OptionCommandService {

    Long create(OptionCreateDto dto);

    void updateOptionStatus(Long optionId, OptionStatus status);

    void updateOptionTotalQuantity(Long optionId, Integer totalQuantity);
}