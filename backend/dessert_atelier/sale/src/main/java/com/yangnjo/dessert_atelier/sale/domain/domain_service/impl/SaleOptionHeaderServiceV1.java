package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangnjo.dessert_atelier.sale.domain.domain_service.SaleOptionHeaderService;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeaderType;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionHeaderRepository;
import com.yangnjo.dessert_atelier.sale.exception.SaleOptionHeaderNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleOptionHeaderServiceV1 implements SaleOptionHeaderService {

    private final SaleOptionHeaderRepository saleOptionHeaderRepo;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void updateHeader(Long saleOptionHeaderId, String header) {
        SaleOptionHeader saleOptionHeader = findSaleOptionHeaderById(saleOptionHeaderId);

        if (saleOptionHeader.getHeaderType() == SaleOptionHeaderType.COMBINATION) {
            String oldHeader = saleOptionHeader.getHeader();
            TypeReference<List<String>> listType = new TypeReference<List<String>>() {
            };

            List<String> oldHeaderList = null;
            List<String> headerList = null;
            try {
                oldHeaderList = objectMapper.readValue(oldHeader, listType);
                headerList = objectMapper.readValue(header, listType);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("헤더 설명이 잘못 되었습니다.");
            }

            if (oldHeaderList.size() != headerList.size()) {
                throw new IllegalArgumentException("헤더 리스트의 길이가 일치하지 않습니다.");
            }
        }

        saleOptionHeader.setHeader(header);

    }

    private SaleOptionHeader findSaleOptionHeaderById(Long saleOptionHeaderId) {
        return saleOptionHeaderRepo.findById(saleOptionHeaderId).orElseThrow(SaleOptionHeaderNotFoundException::new);
    }

}
