package com.yangnjo.dessert_atelier.domain.service;

import java.util.List;

import com.yangnjo.dessert_atelier.common.dto.address.AddressDeleteDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressSaveDto;
import com.yangnjo.dessert_atelier.common.dto.address.AddressSetDefaultDto;
import com.yangnjo.dessert_atelier.db.entity.Users;

public interface AddressService {

    AddressDto getDefaultAddress(Users user);

    List<AddressDto> getAddresses(Users user);

    AddressDto saveAddress(AddressSaveDto addressDto);

    void setDefaultAddress(AddressSetDefaultDto addressDto);

    void deleteAddress(AddressDeleteDto addressDto);

}