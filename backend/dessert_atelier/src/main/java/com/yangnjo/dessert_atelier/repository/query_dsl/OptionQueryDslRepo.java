package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.OptionFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;

public interface OptionQueryDslRepo {

    List<OptionSimpleDto> searchSimplesWithCondition(Long dpId);

    List<OptionFlatDto> searchWithCondition(Long dpId);

}