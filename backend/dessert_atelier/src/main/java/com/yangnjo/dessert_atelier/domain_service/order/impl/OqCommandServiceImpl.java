package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantity;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantityStatus;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain_service.order.OptionQuantityCommandService;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OptionQuantityCreateDto;
import com.yangnjo.dessert_atelier.domain_service.order.exception.DppNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OptionQuantityNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OrderNotFoundException;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.OqRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class OqCommandServiceImpl implements OptionQuantityCommandService {

    private final OqRepository oqRepository;
    private final OrderRepository orderRepository;
    private final DisplayProductPresetRepository displayProductPresetRepository;
    private final OptionRepository optionRepository;

    @Override
    public Long createOptionQuantity(final OptionQuantityCreateDto dto) {
        Orders order = findOrderById(dto.getOrderCode());
        DisplayProductPreset dpp = findDppById(dto.getDppId());
        List<Option> options = optionRepository.findAllById(dto.getOptionIds());
        OptionQuantity oq = dto.toEntity(order, dpp, options);
        OptionQuantity savedOq = oqRepository.save(oq);
        return savedOq.getId();
    }

    @Override
    public void updateOptionQuantityStatus(Long oqId, OptionQuantityStatus status) {
        OptionQuantity oq = findOptionQuantityById(oqId);
        oq.setStatus(status);
    }

    /*
     * batch 전용 함수
     */
    @Override
    public void deleteOptionQuantity(Long oqId) {
        oqRepository.deleteById(oqId);
    }

    private Orders findOrderById(Long orderCode) {
        return orderRepository.findById(orderCode).orElseThrow(OrderNotFoundException::new);
    }

    private OptionQuantity findOptionQuantityById(Long oqId) {
        OptionQuantity oq = oqRepository.findById(oqId)
                .orElseThrow(() -> new OptionQuantityNotFoundException());
        return oq;
    }

    private DisplayProductPreset findDppById(Long dppId) {
        DisplayProductPreset dpp = displayProductPresetRepository.findById(dppId)
                .orElseThrow(() -> new DppNotFoundException());
        return dpp;
    }

}
