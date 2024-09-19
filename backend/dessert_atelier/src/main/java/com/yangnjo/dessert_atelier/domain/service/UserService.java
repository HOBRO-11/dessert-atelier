package com.yangnjo.dessert_atelier.domain.service;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.dto.user.UserDto;
import com.yangnjo.dessert_atelier.common.dto.user.UserJoinDto;
import com.yangnjo.dessert_atelier.common.dto.user.UserModifyDto;
import com.yangnjo.dessert_atelier.common.dto.user.UserSearchDto;
import com.yangnjo.dessert_atelier.db.entity.Users;
import com.yangnjo.dessert_atelier.db.model.UserStatus;

public interface UserService {
    
    UserDto join(UserJoinDto dto);

    Page<UserDto> findUsers(UserSearchDto dto);

    Long count(UserStatus userStatus);

    boolean modify(UserModifyDto dto);

    boolean delete(Users user);

}
