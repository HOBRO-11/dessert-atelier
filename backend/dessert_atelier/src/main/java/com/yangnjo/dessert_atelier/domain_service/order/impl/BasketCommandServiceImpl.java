package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.member.Member;
import com.yangnjo.dessert_atelier.domain_model.order.Basket;
import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.BasketCommandService;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketOverMaxCountException;
import com.yangnjo.dessert_atelier.repository.member.MemberRepository;
import com.yangnjo.dessert_atelier.repository.order.BasketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class BasketCommandServiceImpl implements BasketCommandService {

    private final BasketRepository basketRepository;
    private final MemberRepository memberRepository;
    private static final int BASKET_MAX_COUNT = 30;

    @Override
    public void removeProperty(Long memberId, List<Long> optionIds) {
        Member member = findMemberById(memberId);
        Basket basket = findBasketByMemberId(member);
        List<BasketProperty> bps = basket.getProperties();

        bps.removeIf(bp -> bp.getOptionIds().equals(optionIds));
    }

    @Override
    public void addProperties(Long memberId, final List<BasketProperty> properties) {
        Member member = findMemberById(memberId);
        Basket basket = findBasketByMemberId(member);
        List<BasketProperty> bps = basket.getProperties();

        if (bps.size() >= BASKET_MAX_COUNT || properties.size() >= BASKET_MAX_COUNT) {
            throw new BasketOverMaxCountException(String.valueOf(BASKET_MAX_COUNT));
        }

        processAddProperties(properties, bps);
    }

    private void processAddProperties(final List<BasketProperty> properties, List<BasketProperty> bps) {
        Map<List<Long>, Integer> quantityByOptionIds = properties.stream()
                .collect(Collectors.toMap(bp -> bp.getOptionIds(), bp -> bp.getQuantity()));

        // 이미 Basket에 존재하는 상품인 경우, 수량을 더한다.
        {
            Iterator<BasketProperty> iterator = bps.iterator();
            while (iterator.hasNext()) {
                if (bps.size() >= BASKET_MAX_COUNT) {
                    throw new BasketOverMaxCountException(String.valueOf(BASKET_MAX_COUNT));
                }

                BasketProperty currentBp = iterator.next();
                List<Long> currentOptionIds = currentBp.getOptionIds();
                Integer quantity = quantityByOptionIds.get(currentOptionIds);

                if (quantity == null) {
                    continue;
                }

                currentBp.addQuantity(quantity);
                quantityByOptionIds.remove(currentOptionIds);
            }
        }

        // Basket에 없는 경우, 새로운 상품을 추가한다.
        Iterator<Entry<List<Long>, Integer>> iterator = quantityByOptionIds.entrySet().iterator();
        while (iterator.hasNext()) {
            if (bps.size() >= BASKET_MAX_COUNT) {
                throw new BasketOverMaxCountException(String.valueOf(BASKET_MAX_COUNT));
            }

            Entry<List<Long>, Integer> entry = iterator.next();
            BasketProperty bp = new BasketProperty(entry.getKey());
            bp.addQuantity(entry.getValue());
            bps.add(bp);
        }
    }

    private Basket findBasketByMemberId(Member member) {
        return basketRepository.findByMemberId(member.getId())
                .orElse(basketRepository.save(new Basket(member)));
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }
}
