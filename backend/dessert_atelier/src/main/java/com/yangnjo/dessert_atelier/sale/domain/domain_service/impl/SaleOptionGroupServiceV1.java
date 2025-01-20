package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.sale.domain.domain_service.SaleOptionGroupService;
import com.yangnjo.dessert_atelier.sale.domain.domain_service.SaleOptionGroupValidator;
import com.yangnjo.dessert_atelier.sale.domain.entity.Product;
import com.yangnjo.dessert_atelier.sale.domain.entity.ProductQuantity;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOption;
import com.yangnjo.dessert_atelier.sale.domain.entity.SaleOptionHeader;
import com.yangnjo.dessert_atelier.sale.domain.entity.SalePage;
import com.yangnjo.dessert_atelier.sale.domain.repository.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.ProductRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionHeaderRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SaleOptionRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.SalePageRepository;
import com.yangnjo.dessert_atelier.sale.dto.SaleOptionGroupCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.SalePageNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleOptionGroupServiceV1 implements SaleOptionGroupService {

    private final SaleOptionGroupValidator validator;
    private final SalePageRepository salePageRepo;
    private final SaleOptionHeaderRepository saleOptionHeaderRepo;
    private final SaleOptionRepository saleOptionRepo;
    private final ProductQuantityRepository productQuantityRepo;
    private final ProductRepository productRepo;

    @Override
    @Transactional
    public void create(List<SaleOptionGroupCreateDto> saleOptionGroups) {

        validator.validateForCreate(saleOptionGroups);

        Long salePageId = saleOptionGroups.get(0).getSalePageId();
        SalePage salePage = findSalePageById(salePageId);

        saleOptionGroups.forEach(groupDto -> {
            SaleOptionHeader saleOptionHeaderEntity = groupDto.toSaleOptionHeaderEntity(salePage);
            SaleOptionHeader saleOptionHeader = saleOptionHeaderRepo.save(saleOptionHeaderEntity);
            processToCreateOption(groupDto, saleOptionHeader);
            salePage.setOptionHeaderId(groupDto.getNumbering(), saleOptionHeader.getId());
        });

    }

    private SalePage findSalePageById(Long salePageId) {
        return salePageRepo.findById(salePageId).orElseThrow(SalePageNotFoundException::new);
    }

    @Override
    public void addGroup(SaleOptionGroupCreateDto saleOptionGroup) {

        validator.validateForAdd(saleOptionGroup);

        Integer numbering = saleOptionGroup.getNumbering();
        SalePage salePage = findSalePageById(saleOptionGroup.getSalePageId());

        SaleOptionHeader saleOptionHeaderEntity = saleOptionGroup.toSaleOptionHeaderEntity(salePage);
        SaleOptionHeader saleOptionHeader = saleOptionHeaderRepo.save(saleOptionHeaderEntity);
        processToCreateOption(saleOptionGroup, saleOptionHeader);

        salePage.setOptionHeaderId(numbering, saleOptionHeader.getId());
    }

    private void processToCreateOption(SaleOptionGroupCreateDto saleOptionGroup, SaleOptionHeader saleOptionHeader) {
        saleOptionGroup.getSaleOptions().forEach(saleOptionDto -> {
            SaleOption saleOptionEntity = saleOptionDto.toEntity(saleOptionHeader);
            SaleOption saleOption = saleOptionRepo.save(saleOptionEntity);

            saleOptionDto.getProductQuantities().forEach(productQuantityDto -> {
                Long productId = productQuantityDto.getProductId();
                Product product = productRepo.findById(productId).get();
                ProductQuantity productQuantityEntity = productQuantityDto.toEntity(product, saleOption);
                productQuantityRepo.save(productQuantityEntity);
            });
        });
    }

}
