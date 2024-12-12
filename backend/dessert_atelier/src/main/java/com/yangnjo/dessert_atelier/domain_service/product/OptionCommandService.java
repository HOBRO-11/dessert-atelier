package com.yangnjo.dessert_atelier.domain_service.product;

import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.OptionCreateDto;

public interface OptionCommandService {

    Long create(OptionCreateDto dto);

    void updateOptionStatus(Long optionId, OptionStatus status);

}