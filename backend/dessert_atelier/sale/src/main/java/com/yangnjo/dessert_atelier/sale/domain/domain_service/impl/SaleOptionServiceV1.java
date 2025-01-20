package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangnjo.dessert_atelier.sale.domain.domain_service.SaleOptionService;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeaderType;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionStatus;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionHeaderRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionRepository;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.SaleOptionAlreadyExistsException;
import com.yangnjo.dessert_atelier.sale.exception.SaleOptionHeaderNotFoundException;
import com.yangnjo.dessert_atelier.sale.exception.SaleOptionNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleOptionServiceV1 implements SaleOptionService {

    private final SaleOptionRepository saleOptionRepo;
    private final SaleOptionHeaderRepository saleOptionHeaderRepo;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Long create(SaleOptionCreateDto dto) {
        SaleOptionHeader oh = findOptionHeaderById(dto);
        if (dto.getExplanation() == null || dto.getPrice() == null) {
            throw new IllegalArgumentException("description나 price를 입력해주세요.");
        }
        if (dto.getPrice() == null || dto.getPrice() < 0) {
            throw new IllegalArgumentException("0 이상인 price를 입력해주세요.");
        }
        SaleOption entity = dto.toEntity(oh);

        validateOption(entity);

        return saleOptionRepo.save(entity).getId();
    }

    @Override
    public void updateStatus(Long optionId, SaleOptionStatus status) {
        SaleOption option = findOptionById(optionId);
        option.setStatus(status);
    }

    private void validateOption(SaleOption entity) {
        boolean isExist = saleOptionRepo.existByEntity(entity);
        if (isExist) {
            throw new SaleOptionAlreadyExistsException();
        }

        SaleOptionHeader saleOptionHeader = entity.getSaleOptionHeader();
        SaleOptionHeaderType headerType = saleOptionHeader.getHeaderType();
        if (headerType == SaleOptionHeaderType.COMBINATION) {
            String header = saleOptionHeader.getHeader();
            String explanation = entity.getExplanation();
            List<String> headerList;
            List<String> optionExplanationList;

            try {
                TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
                };
                headerList = mapper.readValue(header, typeRef);
                optionExplanationList = mapper.readValue(explanation, typeRef);
            } catch (IllegalArgumentException | JsonProcessingException e) {
                throw new IllegalArgumentException("옵션 설명이 헤더 타입과 일치하지 않습니다.");
            }

            if (headerList.size() != optionExplanationList.size()) {
                throw new IllegalArgumentException("헤더 리스트와 설명 리스트의 길이가 일치하지 않습니다.");
            }
        }

    }

    private SaleOptionHeader findOptionHeaderById(SaleOptionCreateDto dto) {
        return saleOptionHeaderRepo.findById(dto.getSaleOptionHeaderId())
                .orElseThrow(SaleOptionHeaderNotFoundException::new);
    }

    private SaleOption findOptionById(Long optionId) {
        SaleOption option = saleOptionRepo.findById(optionId)
                .orElseThrow(() -> new SaleOptionNotFoundException());
        return option;
    }

}
