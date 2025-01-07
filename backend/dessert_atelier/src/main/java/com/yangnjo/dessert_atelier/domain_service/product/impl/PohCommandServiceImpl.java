package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain_model.product.ProductOptionHeader;
import com.yangnjo.dessert_atelier.domain_service.product.ProductOptionHeaderCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductOptionHeaderCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductOptionHeaderUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.repository.product.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.product.ProductOptionHeaderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PohCommandServiceImpl implements ProductOptionHeaderCommandService {

    private final ProductOptionHeaderRepository ohRepository;
    private final DisplayProductRepository dpRepository;

    @Override
    public Long create(Long displayProductId, ProductOptionHeaderCreateDto dto) {

        DisplayProduct dp = findDpById(displayProductId);

        if (dto.getOptionHeaderName() == null || dto.getOptionHeaderName().isEmpty()) {
            throw new IllegalArgumentException("optionHeaderName를 입력해주세요.");
        }

        if (dto.getHeaderType() == null) {
            throw new IllegalArgumentException("headerType를 입력해주세요.");
        }

        ProductOptionHeader entity = dto.toEntity(dp);
        if(ohRepository.existByEntity(entity)){
            throw new IllegalStateException("이미 존재하는 옵션 헤더입니다. : " + dto.getOptionHeaderName() );
        }

        return ohRepository.save(entity).getId();
    }

    @Override
    public void update(Long optionHeaderId, ProductOptionHeaderUpdateDto dto) {
        ProductOptionHeader oh = findOptionHeaderById(optionHeaderId);
        String optionHeaderName = dto.getOptionHeaderName();

        if (optionHeaderName == null || optionHeaderName.isEmpty()) {
            throw new IllegalArgumentException("optionHeaderName를 입력해주세요.");
        }

        oh.setOptionHeaderName(optionHeaderName);
    }

    private ProductOptionHeader findOptionHeaderById(Long optionHeaderId) {
        if (optionHeaderId == null) {
            throw new IllegalArgumentException("optionHeaderId를 입력해주세요.");
        }
        return ohRepository.findById(optionHeaderId).orElseThrow(DisplayProductNotFountException::new);
    }

    private DisplayProduct findDpById(Long dpId) {
        if (dpId == null) {
            throw new IllegalArgumentException("dpId를 입력해주세요.");
        }
        return dpRepository.findById(dpId).orElseThrow(DisplayProductNotFountException::new);
    }
}
