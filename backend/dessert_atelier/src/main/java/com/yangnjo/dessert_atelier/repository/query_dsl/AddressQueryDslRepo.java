package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.AddressFlatDto;

public interface AddressQueryDslRepo {

    List<AddressFlatDto> searchWithCondition(Long userId);

}