package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.sale.domain.domain_service.ShopPageService;
import com.yangnjo.dessert_atelier.sale.domain.domain_service.ShopPageValidator;
import com.yangnjo.dessert_atelier.sale.domain.entity.ShopPage;
import com.yangnjo.dessert_atelier.sale.domain.repository.ShopPageRepository;
import com.yangnjo.dessert_atelier.sale.dto.ShopPageCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.ShopPageNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopPageServiceV1 implements ShopPageService {

    private final ShopPageRepository shopPageRepo;
    private final ShopPageValidator shopPageValidator;

    @Override
    public void updateDuration(Long shopPageId, LocalDateTime startedDateAt, LocalDateTime endedDateAt) {
        shopPageValidator.validateForUpdateDate(shopPageId, startedDateAt, endedDateAt);

        ShopPage shopPage = findShopPageById(shopPageId);
        shopPage.setDateAt(startedDateAt, endedDateAt);
    }

    @Override
    public Long create(ShopPageCreateDto dto) {
        shopPageValidator.validateForCreate(dto);

        ShopPage shopPage = shopPageRepo.save(dto.toEntity());
        if (dto.isDefault()) {
            shopPage.setDefaultToTrue();
        }

        return shopPage.getId();
    }

    // FIXME
    @Override
    public void updateIsDefault(Long shopPageId, boolean isDefault) {
        shopPageValidator.validateForUpdateDefault(shopPageId, isDefault);

        ShopPage newShopPage = findShopPageById(shopPageId);
        if (isDefault) {
            ShopPage oldShopPage = shopPageRepo.findByIsDefault(true).get();
            oldShopPage.setDefaultToFalse();

            newShopPage.setDefaultToTrue();
            return;
        }
        newShopPage.setDefaultToFalse();
    }

    @Override
    public void updateIsDeleteAtEndOfEvent(Long shopPageId, boolean isDeleteAtEndOfEvent) {
        shopPageValidator.validateForUpdateIsDeleteAtEndOfEvent(shopPageId, isDeleteAtEndOfEvent);

        findShopPageById(shopPageId).setDeleteAtEndOfEvent(isDeleteAtEndOfEvent);
    }

    @Override
    public void updateNaming(Long shopPageId, String naming) {
        shopPageValidator.validateForUpdateNaming(shopPageId, naming);

        findShopPageById(shopPageId).setNaming(naming);
    }

    @Override
    public void updateSalePageIds(Long shopPageId, List<Long> salePageIds) {
        shopPageValidator.validateForUpdateSalePageIds(salePageIds);

        findShopPageById(shopPageId).setSalePageIds(salePageIds);
    }

    @Override
    public void delete(Long shopPageId) {
        shopPageValidator.validateForDelete(shopPageId);
        shopPageRepo.deleteById(shopPageId);
    }

    private ShopPage findShopPageById(Long shopPageId) {
        return shopPageRepo.findById(shopPageId).orElseThrow(ShopPageNotFoundException::new);
    }

}
