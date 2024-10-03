package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.DeliveryFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;

public interface DeliveryQueryDslRepo {

    List<DeliveryFlatDto> searchWithCondition(PageOption pageOption, String code);

    Long countWithCondition(String code);

}