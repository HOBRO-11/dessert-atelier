package com.yangnjo.dessert_atelier.service.address.dto;

import java.util.List;

import com.yangnjo.dessert_atelier.domain_model.value_type.Destination;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseForm {

    List<DestinationDetail> destinations;

    @AllArgsConstructor
    public static class DestinationDetail {
        String naming;
        Destination destination;
        boolean isDefault;
    }
}
