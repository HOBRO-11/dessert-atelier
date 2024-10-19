package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.product.Component;
import com.yangnjo.dessert_atelier.domain_service.product.ComponentCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ComponentCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ComponentNotFoundException;
import com.yangnjo.dessert_atelier.repository.ComponentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ComponentCommandServiceImpl implements ComponentCommandService {

    private final ComponentRepository componentRepository;

    @Override
    public Long createComponent(final ComponentCreateDto dto) {
        Component component = dto.toEntity();
        Component savedComponent = componentRepository.save(component);
        return savedComponent.getId();
    }

    @Override
    public void updateComponentName(Long componentId, String name) {
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new ComponentNotFoundException());
        component.setName(name);
    }

    /*
     * batch 전용 함수
     */
    @Override
    public void deleteComponent(Long componentId) {
        componentRepository.deleteById(componentId);
    }
}
