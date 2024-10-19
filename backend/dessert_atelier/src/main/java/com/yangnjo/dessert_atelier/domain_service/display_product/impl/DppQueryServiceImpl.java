package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_service.display_product.DisplayProductPresetQueryService;
import com.yangnjo.dessert_atelier.repository.dto.DppDto;
import com.yangnjo.dessert_atelier.repository.dto.DppSimpleDto;
import com.yangnjo.dessert_atelier.repository.query.DppQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DppQueryServiceImpl implements DisplayProductPresetQueryService {

    private final DppQueryRepo dppQueryRepo;

    @Override
    public Optional<DppDto> getDPP(Long dppId) {
        return dppQueryRepo.find(dppId);
    }

    @Override
    public Page<DppSimpleDto> getSimpleDPPsByDpId(Long dpId, PageOption pageOption, PeriodOption periodOption) {
        List<DppSimpleDto> dtos = dppQueryRepo.findSimpleByDpId(dpId, pageOption, periodOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = dppQueryRepo.countSimpleByDpId(dpId, periodOption);
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public Page<DppSimpleDto> getSimpleDPPsLikeNaming(String naming, PageOption pageOption, PeriodOption periodOption) {
        List<DppSimpleDto> dtos = dppQueryRepo.findSimpleLikeNaming(naming, pageOption, periodOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = dppQueryRepo.countSimpleLikeNaming(naming, periodOption);
        return PageResponse.of(dtos, pageOption, total);
    }

}
