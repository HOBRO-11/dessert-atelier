package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.TodoStatus;
import com.yangnjo.dessert_atelier.repository.dto.DateOption;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.TodoOrderCartsDto;
import com.yangnjo.dessert_atelier.repository.dto.TodoSimpleDto;

public interface TodoQueryDslRepo {

    List<TodoSimpleDto> searchSimpleWithCondition(PageOption pageOption, DateOption dateOption, TodoStatus status);

    List<TodoOrderCartsDto> searchWithCondition(PageOption pageOption, DateOption dateOption, TodoStatus status);

    Long countWithCondition(DateOption dateOption, TodoStatus status);

}