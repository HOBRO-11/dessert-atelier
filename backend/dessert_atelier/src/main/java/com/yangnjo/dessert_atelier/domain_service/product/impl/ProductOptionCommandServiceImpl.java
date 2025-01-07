package com.yangnjo.dessert_atelier.domain_service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_model.product.ProductOption;
import com.yangnjo.dessert_atelier.domain_model.product.ProductOptionHeader;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.domain_service.product.ProductOptionCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductOptionCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductOptionHeaderNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.repository.product.ProductOptionHeaderRepository;
import com.yangnjo.dessert_atelier.repository.product.OptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class ProductOptionCommandServiceImpl implements ProductOptionCommandService {

    private final OptionRepository optionRepository;
    private final ProductOptionHeaderRepository ohRepository;

    @Override
    public Long create(ProductOptionCreateDto dto) {
        ProductOptionHeader oh = findOptionHeaderById(dto);
        if(dto.getDescription() == null || dto.getPrice() == null) {
            throw new IllegalArgumentException("description나 price를 입력해주세요.");
        }
        if(dto.getPrice() == null || dto.getPrice() < 0) {
            throw new IllegalArgumentException("0 이상인 price를 입력해주세요.");
        }
        return optionRepository.save(dto.toEntity(oh)).getId();
    }

    @Override
    public void updateOptionStatus(Long optionId, OptionStatus status) {
        ProductOption option = findOptionById(optionId);
        option.setOptionStatus(status);
    }

    private ProductOptionHeader findOptionHeaderById(ProductOptionCreateDto dto) {
        return ohRepository.findById(dto.getOptionHeaderId()).orElseThrow(ProductOptionHeaderNotFoundException::new);
    }

    private ProductOption findOptionById(Long optionId) {
        ProductOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionNotFoundException());
        return option;
    }
}
