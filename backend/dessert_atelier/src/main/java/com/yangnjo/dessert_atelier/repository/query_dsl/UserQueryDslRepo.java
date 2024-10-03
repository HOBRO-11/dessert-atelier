package com.yangnjo.dessert_atelier.repository.query_dsl;

import java.util.List;

import com.yangnjo.dessert_atelier.domain.UserStatus;
import com.yangnjo.dessert_atelier.repository.dto.PageOption;
import com.yangnjo.dessert_atelier.repository.dto.UserFlatDto;

public interface UserQueryDslRepo {

    List<UserFlatDto> searchWithStatus(PageOption pageOption, UserStatus userStatus);

    Long countWithStatus(UserStatus userStatus);

}