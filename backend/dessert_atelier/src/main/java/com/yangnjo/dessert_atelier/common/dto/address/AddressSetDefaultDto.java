package com.yangnjo.dessert_atelier.common.dto.address;

import com.yangnjo.dessert_atelier.db.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressSetDefaultDto {

    private Users users;

    private Long addressId;
}
