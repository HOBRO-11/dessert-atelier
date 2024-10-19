package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.product.ComponentQueryService;
import com.yangnjo.dessert_atelier.repository.dto.ComponentDto;
import com.yangnjo.dessert_atelier.repository.query.ComponentQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ComponentQueryServiceImpl implements ComponentQueryService {

    private final ComponentQueryRepo componentQueryRepo;

    @Override
    public List<ComponentDto> getComponentsByIds(List<Long> ids) {
        return componentQueryRepo.findByIds(ids);
    }
}
