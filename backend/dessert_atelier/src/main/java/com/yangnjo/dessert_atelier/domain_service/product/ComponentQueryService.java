package com.yangnjo.dessert_atelier.domain_service.product;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.ComponentDto;

public interface ComponentQueryService {

  List<ComponentDto> getComponentsByIds(List<Long> ids);

}