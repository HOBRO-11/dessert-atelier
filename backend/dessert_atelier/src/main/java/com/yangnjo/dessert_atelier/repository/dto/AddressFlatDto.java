package com.yangnjo.dessert_atelier.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressFlatDto {

    private Long id;

    private String naming;

    private String postCode;

    private String detailAddress;

    private String receiver;

    private int phone;

    private boolean isDefault;
}
