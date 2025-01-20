package com.yangnjo.dessert_atelier.member.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MemberAddressProperties {
    @Value("${domain.member.member_address.max_count}")
    private int maxCount;
}
