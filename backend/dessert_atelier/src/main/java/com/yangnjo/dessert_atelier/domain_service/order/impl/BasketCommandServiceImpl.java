package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.order.Basket;
import com.yangnjo.dessert_atelier.domain.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.BasketCommandService;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketOverMaxCountException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketPropertyNotFoundException;
import com.yangnjo.dessert_atelier.repository.BasketRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class BasketCommandServiceImpl implements BasketCommandService {

    private final BasketRepository basketRepository;
    private final MemberRepository memberRepository;
    private static final int BASKET_MAX_COUNT = 20;

    @Override
    public Long createBasket(Long memberId) {
        Member member = findMemberById(memberId);
        Basket basket = new Basket(member);
        Basket savedBasket = basketRepository.save(basket);
        return savedBasket.getId();
    }

    @Override
    public void addProperties(Long memberId, final List<BasketProperty> properties) {
        Member member = findMemberById(memberId);

        Basket basket = member.getBasket();
        if (basket == null) {
            throw new BasketNotFoundException();
        }

        List<BasketProperty> basketProperties = basket.getProperties();
        checkBasketCountLtMax(basketProperties);

        processAddProperties(properties, basket, basketProperties);
    }

    @Override
    public void removeProperty(Long memberId, Long dppId, final List<Long> optionIds) {
        Member member = findMemberById(memberId);

        Basket basket = member.getBasket();
        if (basket == null) {
            throw new BasketNotFoundException();
        }

        List<BasketProperty> properties = basket.getProperties();
        if (properties.isEmpty()) {
            throw new BasketPropertyNotFoundException();
        }
        properties.remove(new BasketProperty(dppId, optionIds));
    }

    private void checkBasketCountLtMax(List<BasketProperty> properties) {
        if (properties.size() >= BASKET_MAX_COUNT) {
            throw new BasketOverMaxCountException();
        }
        ;
    }

    private void processAddProperties(List<BasketProperty> properties, Basket basket,
            List<BasketProperty> basketProperties) {
        properties.forEach(
                property -> {
                    if (basketProperties.contains(property)) {
                        basketProperties.forEach(bp -> {
                            if (bp != null && bp.equals(property)) {
                                bp.addQuantity(property.getQuantity());
                            }
                        });
                    } else {
                        basket.addProperty(property);
                    }
                });
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }
}
