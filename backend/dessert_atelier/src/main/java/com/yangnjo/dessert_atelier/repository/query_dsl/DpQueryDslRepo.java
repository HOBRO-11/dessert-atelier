package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.repository.dto.DpFlatDto;
import com.yangnjo.dessert_atelier.repository.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;

public interface DpQueryDslRepo {

    Optional<DpFlatDto> searchWithCondition(PageOption pageOption, Long dpId);

    List<DpSimpleDto> searchSimplesWithCondition(PageOption pageOption, Long dpId);

}