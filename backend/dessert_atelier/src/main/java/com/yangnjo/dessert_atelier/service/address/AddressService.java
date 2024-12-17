package com.yangnjo.dessert_atelier.service.address;

import java.util.List;

import com.yangnjo.dessert_atelier.service.address.dto.AddressCreateForm;
import com.yangnjo.dessert_atelier.service.address.dto.AddressResponseForm;
import com.yangnjo.dessert_atelier.service.address.dto.AddressUpdateForm;

public interface AddressService {

    AddressResponseForm getAddresses(Long memberId);

    AddressResponseForm getDefaultAddress(Long memberId);
    
    void create(Long memberId, List<AddressCreateForm> forms);

    void update(Long memberId, AddressUpdateForm form);

    void setDefault(Long memberId, Long addressId);

    void delete(Long memberId, Long addressId);
}
