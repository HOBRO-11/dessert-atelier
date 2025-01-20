package com.yangnjo.dessert_atelier.orders.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.orders.domain.domain_service.CartOptionService;
import com.yangnjo.dessert_atelier.orders.domain.entity.CartOption;
import com.yangnjo.dessert_atelier.orders.domain.entity.value_type.CartOptionProperty;
import com.yangnjo.dessert_atelier.orders.domain.repostiory.CartOptionRepository;
import com.yangnjo.dessert_atelier.orders.exception.CartOptionMaxCountException;
import com.yangnjo.dessert_atelier.orders.properties.OrderProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class CartOptionServiceV1 implements CartOptionService {

    private final CartOptionRepository cartOptionRepo;
    private final OrderProperties cartOptionProp;

    @Override
    public void removeProperty(Long memberId, List<CartOptionProperty> optionIds) {
        CartOption cart = findBasketByMemberId(memberId);
        List<CartOptionProperty> bps = cart.getProperties();

        bps.removeIf(bp -> bp.getOptionIds().equals(optionIds));
    }

    @Override
    public void addProperties(Long memberId, final List<CartOptionProperty> properties) {
        int maxCount = cartOptionProp.getMaxCount();
        CartOption cartOption = findBasketByMemberId(memberId);
        List<CartOptionProperty> cartOptionProperties = cartOption.getProperties();

        if (cartOptionProperties.size() >= maxCount || properties.size() >= maxCount) {
            throw new CartOptionMaxCountException(String.valueOf(maxCount));
        }

        properties.forEach(prop -> cartOption.addProperty(prop));
    }

    private CartOption findBasketByMemberId(Long memberId) {
        return cartOptionRepo.findByMemberId(memberId)
                .orElseGet(() -> cartOptionRepo.save(CartOption.builder().memberId(memberId).build()));
    }

}
