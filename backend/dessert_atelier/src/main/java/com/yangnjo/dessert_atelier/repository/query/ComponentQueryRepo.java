package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;

import com.yangnjo.dessert_atelier.repository.dto.ComponentDto;

public interface ComponentQueryRepo {

  List<ComponentDto> findByIds(List<Long> ids);

}