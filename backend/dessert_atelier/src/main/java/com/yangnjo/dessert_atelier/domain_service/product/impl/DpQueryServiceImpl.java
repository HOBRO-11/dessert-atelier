package com.yangnjo.dessert_atelier.domain_service.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.query.DpQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DpQueryServiceImpl implements DisplayProductQueryService {

    private final DpQueryRepo dpQueryRepo;

    @Override
    public Optional<DpDto> getById(Long id) {
        return dpQueryRepo.findById(id);
    }

    @Override
    public Page<DpSimpleDto> getAllSimpleByDpStatus(DisplayProductStatus displayProductStatus, PageOption pageOption) {
        List<DpSimpleDto> dtos = dpQueryRepo.findAllSimpleByDpStatus(displayProductStatus, pageOption);

        return PageResponse.of(dtos, pageOption, () -> dpQueryRepo.countByDpStatus(displayProductStatus));
    }

    @Override
    public Page<DpSimpleDto> getAllSimpleByExceptDpStatus(DisplayProductStatus displayProductStatus,
            PageOption pageOption) {
        List<DpSimpleDto> dtos = dpQueryRepo.findAllSimpleByExceptDpStatus(displayProductStatus, pageOption);
        return PageResponse.of(dtos, pageOption, () -> dpQueryRepo.countByExceptDpStatus(displayProductStatus));

    }

    @Override
    public List<DpSimpleDto> getAllSimpleByIdIn(List<Long> ids) {
        return dpQueryRepo.findAllSimpleByIdIn(ids);
    }
}
