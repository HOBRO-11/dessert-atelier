package com.yangnjo.dessert_atelier.db.repository;

import java.util.Optional;

import com.yangnjo.dessert_atelier.common.dto.address.AddressDto;
import com.yangnjo.dessert_atelier.db.entity.Users;

public interface AddressQueryDslRepo {

    Optional<AddressDto> findDefaultAddress(Users users);

}