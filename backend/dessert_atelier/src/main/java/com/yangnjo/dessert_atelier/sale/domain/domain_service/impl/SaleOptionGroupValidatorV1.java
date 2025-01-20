package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangnjo.dessert_atelier.sale.domain.domain_service.SaleOptionGroupValidator;
import com.yangnjo.dessert_atelier.sale.domain.entity.Product;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeaderType;
import com.yangnjo.dessert_atelier.sale.domain.entity.SalePage;
import com.yangnjo.dessert_atelier.sale.domain.repository.ProductRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionHeaderRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SalePageRepository;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionCreateDto;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionGroupCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.sale.exception.SaleOptionHeaderAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleOptionGroupValidatorV1 implements SaleOptionGroupValidator {

    private final SalePageRepository salePageRepo;
    private final SaleOptionHeaderRepository saleOptionHeaderRepo;
    private final ProductRepository productRepo;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void validateForCreate(List<SaleOptionGroupCreateDto> saleOptionGroup) {

        Long salePageId = saleOptionGroup.get(0).getSalePageId();
        
        // 모두 동일한 판매 페이지를 이용하지는 확인
        checkSalePageId(saleOptionGroup, salePageId);
        
        // salePage에 저장된 옵션이 존재하는지 확인
        SalePage salePage = getSalePage(salePageId);
        if (salePage.getOptionHeaderIds() == null || salePage.getOptionHeaderIds().isEmpty()) {
        } else {
            throw new IllegalArgumentException("이미 옵션이 존재합니다.");
        }

        // 헤더 번호 중복 여부 확인
        checkOptionHeaderNumbering(saleOptionGroup);

        // 헤더 타입에 따른 헤더 이름과 옵션 이름의 길이 확인
        saleOptionGroup.forEach(this::processCheckHeaderType);

        // 옵션 설명이 중복되지 않는지 확인
        saleOptionGroup.forEach(this::conflictOptionExplanationChecker);

        // 존재하는 product인지 확인한다.
        List<Long> productIds = getProductIds(saleOptionGroup);
        productIds.forEach(productId -> findProductById(salePageId));

    }

    private void checkOptionHeaderNumbering(List<SaleOptionGroupCreateDto> saleOptionGroup) {
        Set<Integer> optionHeaderNumbering = new HashSet<>();
        for (SaleOptionGroupCreateDto dto : saleOptionGroup) {
            Integer numbering = dto.getNumbering();
            if (optionHeaderNumbering.contains(numbering)) {
                throw new IllegalArgumentException("헤더 번호가 중복되었습니다.");
            }
            optionHeaderNumbering.add(numbering);
        }
    }

    private void checkSalePageId(List<SaleOptionGroupCreateDto> saleOptionGroup, Long salePageId) {
        saleOptionGroup.forEach(group -> {
            if (group.getSalePageId() != salePageId)
                throw new IllegalArgumentException("판매 페이지가 일치하지 않습니다.");
        });
    }

    private void conflictOptionExplanationChecker(SaleOptionGroupCreateDto saleOptionGroup) {
        String header = saleOptionGroup.getHeader();
        List<SaleOptionCreateDto> saleOptions = saleOptionGroup.getSaleOptions();
        long count = saleOptions.stream().map(saleOption -> saleOption.getExplanation()).distinct().count();

        if (saleOptions.size() != count) {
            throw new IllegalArgumentException("헤더 : " + header + " 아래 중복된 옵션의 설명이 존재합니다.");
        }
    }

    @Override
    public void validateForAdd(SaleOptionGroupCreateDto saleOptionGroup) {
        Long salePageId = saleOptionGroup.getSalePageId();
        SalePage salePage = getSalePage(salePageId);
        List<Long> optionHeaderIds = salePage.getOptionHeaderIds();

        if (optionHeaderIds == null || optionHeaderIds.isEmpty()) {
            throw new IllegalStateException("해당 판매 페이지에는 옵션이 존재하지 않습니다. 추가가 아닌 생성을 진행하세요.");
        }

        // 이미 존재하는 option header인지 확인
        SaleOptionHeader entity = saleOptionGroup.toSaleOptionHeaderEntity(salePage);
        if (saleOptionHeaderRepo.existByEntity(entity)) {
            throw new SaleOptionHeaderAlreadyExistsException(saleOptionGroup.getHeader());
        }

        // 헤더 타입에 따른 헤더 이름과 옵션 이름의 길이 확인
        processCheckHeaderType(saleOptionGroup);

        // 옵션 설명이 중복되지 않는지 확인
        conflictOptionExplanationChecker(saleOptionGroup);

        // 존재하는 product인지 확인
        List<Long> productIds = saleOptionGroup.getSaleOptions().stream()
                .flatMap(saleOption -> saleOption.getProductQuantities().stream())
                .map(pq -> pq.getProductId())
                .collect(Collectors.toList());
        productIds.forEach(productId -> findProductById(salePageId));

    }

    private void processCheckHeaderType(SaleOptionGroupCreateDto optionGroup) {
        TypeReference<List<String>> listType = new TypeReference<List<String>>() {

        };

        SaleOptionHeaderType headerType = optionGroup.getHeaderType();
        if (headerType == SaleOptionHeaderType.COMBINATION) {
            String header = optionGroup.getHeader();
            List<String> headerList;

            try {
                headerList = mapper.readValue(header, listType);
            } catch (IllegalArgumentException | JsonProcessingException e) {
                throw new IllegalArgumentException("헤더 타입이 잘못 되었습니다.");
            }

            optionGroup.getSaleOptions().forEach(saleOption -> {
                String explanation = saleOption.getExplanation();
                List<String> explanationList;

                try {
                    explanationList = mapper.readValue(explanation, listType);
                } catch (IllegalArgumentException | JsonProcessingException e) {
                    throw new IllegalArgumentException("헤더 타입에 맞지 않는 옵션 설명입니다.");
                }

                if (headerList.size() != explanationList.size()) {
                    throw new IllegalArgumentException("헤더 리스트와 설명 리스트의 길이가 일치하지 않습니다.");
                }
            });
        }

    }

    private List<Long> getProductIds(List<SaleOptionGroupCreateDto> saleOptionGroup) {
        return saleOptionGroup.stream().flatMap(group -> group.getSaleOptions().stream())
                .flatMap(saleOption -> saleOption.getProductQuantities().stream()).map(pq -> pq.getProductId())
                .distinct().collect(Collectors.toList());
    }

    private Product findProductById(Long salePageId) {
        return productRepo.findById(salePageId).orElseThrow(ProductNotFoundException::new);
    }

    private SalePage getSalePage(Long salePageId) {
        return salePageRepo.findById(salePageId).orElseThrow(ProductNotFoundException::new);
    }
}
