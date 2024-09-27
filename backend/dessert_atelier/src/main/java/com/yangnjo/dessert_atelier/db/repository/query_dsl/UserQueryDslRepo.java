package com.yangnjo.dessert_atelier.db.repository.query_dsl;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.db.model.UserStatus;

public interface UserQueryDslRepo {

    List<UserDto> findByStatus(int page, int size, UserStatus userStatus, Direction direction);

    Long count(UserStatus userStatus);

}