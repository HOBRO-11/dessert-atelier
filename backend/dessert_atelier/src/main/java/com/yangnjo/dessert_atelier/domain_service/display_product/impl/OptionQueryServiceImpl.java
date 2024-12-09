package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.OptionQueryService;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.OptionNotAvailableException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.repository.dto.OptionDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OptionSimpleDto;
import com.yangnjo.dessert_atelier.repository.query.OptionQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OptionQueryServiceImpl implements OptionQueryService {

    private final OptionQueryRepo optionQueryRepo;

    @Override
    public Page<OptionSimpleDto> getAllOptionsByDppIdAndStatus(Long dppId, OptionStatus status, PageOption pageOption) {
        List<OptionSimpleDto> dtos = optionQueryRepo.findAllByDppIdAndStatus(dppId, status, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        long total = optionQueryRepo.countByDppIdAndStatus(dppId, status);
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public List<OptionSimpleDto> getAllOptionsByDppIdAndStatus(Long dppId, OptionStatus status) {
        return optionQueryRepo.findAllByDppIdAndStatus(dppId, status, null);
    }

    @Override
    public OptionDetailDto getOptionDetail(Long optionId) {
        OptionDetailDto dto = optionQueryRepo.findDetailByOptionId(optionId)
                .orElseThrow(() -> new OptionNotFoundException());
        OptionStatus optionStatus = dto.getOptionStatus();
        if (optionStatus != OptionStatus.AVAILABLE) {
            throw new OptionNotAvailableException();
        }
        return dto;
    }

    @Override
    public List<OptionSimpleDto> getOptionSimpleDtosByOptionIds(List<Long> optionIds) {
        return optionQueryRepo.findByOptionIds(optionIds);
    }
}
