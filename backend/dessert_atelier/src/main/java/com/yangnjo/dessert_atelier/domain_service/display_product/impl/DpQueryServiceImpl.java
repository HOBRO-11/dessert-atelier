package com.yangnjo.dessert_atelier.domain_service.display_product.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.DisplayProductQueryService;
import com.yangnjo.dessert_atelier.repository.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.query.DpQueryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DpQueryServiceImpl implements DisplayProductQueryService {

    private final DpQueryRepo dpQueryRepo;

    @Override
    public Page<DpDto> getAllDisplayProducts(PageOption pageOption) {
        List<DpDto> dtos = dpQueryRepo.findAll(pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = dpQueryRepo.countAll();
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public Page<DpDto> getDisplayProductsBySaleStatusAndNaming(SaleStatus saleStatus, String naming,
            PageOption pageOption) {
        List<DpDto> dtos = dpQueryRepo.findBySaleStatusAndLikeNaming(saleStatus, naming, pageOption);
        int size = dtos.size();
        if (size <= pageOption.getSize()) {
            return PageResponse.ofSizeLePageOptionSize(dtos, pageOption);
        }
        Long total = dpQueryRepo.countBySaleStatusAndLikeNaming(saleStatus, naming);
        return PageResponse.of(dtos, pageOption, total);
    }

    @Override
    public List<DpDto> getDisplayProductsByIds(List<Long> ids) {
        return dpQueryRepo.findByIds(ids);
    }
}
