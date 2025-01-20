package com.yangnjo.dessert_atelier.sale.domain.domain_service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.sale.domain.domain_service.ShopPageValidator;
import com.yangnjo.dessert_atelier.sale.domain.entity.ShopPage;
import com.yangnjo.dessert_atelier.sale.domain.repository.SalePageRepository;
import com.yangnjo.dessert_atelier.sale.domain.repository.ShopPageRepository;
import com.yangnjo.dessert_atelier.sale.dto.ShopPageCreateDto;
import com.yangnjo.dessert_atelier.sale.exception.ShopPageNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopPageValidatorV1 implements ShopPageValidator {

    private final ShopPageRepository shopPageRepository;
    private final SalePageRepository salePageRepository;

    @Override
    public void validateForCreate(ShopPageCreateDto dto) {

        List<Long> salePageIds = new ArrayList<>(dto.getSalePageIds());
        LocalDateTime startedDateAt = dto.getStartedDateAt();
        LocalDateTime endedDateAt = dto.getEndedDateAt();

        checkConflictNaming(null, dto.getNaming());
        checkExistsSalePage(salePageIds);
        List<ShopPage> all = shopPageRepository.findAll();

        if (all.isEmpty()) {
            if (dto.isDefault() == false) {
                throw new IllegalArgumentException("처음 생성한 대문페이지 템플릿은 기본으로 설정되어야 합니다.");
            }

            return;
        } else {
            if (dto.isDefault()) {
                throw new IllegalArgumentException("기본 대문페이지 템플릿은 이미 존재하여 생성할 수 없습니다.");
            }
        }

        dateConflictChecker(startedDateAt, endedDateAt, all, null);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private void checkExistsSalePage(List<Long> salePageIds) {
        List<Long> removableSalePageIds = new ArrayList<>(salePageIds);
        salePageRepository.findAllById(removableSalePageIds).forEach(salePage -> {
            Long currentSalePageId = salePage.getId();
            if (removableSalePageIds.contains(currentSalePageId)) {
                removableSalePageIds.remove(currentSalePageId);
            }
        });

        if (removableSalePageIds.isEmpty() == false) {
            String[] idArray = removableSalePageIds.stream().map(String::valueOf).toArray(String[]::new);
            String errorMessage = String.join(",", idArray) + " 의 상품을 찾을 수 없습니다.";
            throw new IllegalStateException(errorMessage);
        }
    }

    @Override
    public void validateForUpdateDate(Long shopPageId, LocalDateTime startedDateAt, LocalDateTime endedDateAt) {

        if (startedDateAt.isAfter(endedDateAt)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
        if (endedDateAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("종료일은 현재 이후이어야 합니다.");
        }

        List<ShopPage> all = shopPageRepository.findAll();

        dateConflictChecker(startedDateAt, endedDateAt, all, shopPageId);
    }

    @Override
    public void validateForUpdateDefault(Long shopPageId, boolean isDefault) {
        if (isDefault == false) {
            ShopPage shopPage = shopPageRepository.findByIsDefault(true)
                    .orElseThrow(() -> new IllegalStateException("현재 기본 대문페이지 템플릿이 없어 기본 템플릿부터 생성하십시오"));

            if (shopPage.getId() == shopPageId) {
                throw new IllegalArgumentException(
                        "기본 대문페이지 템플릿은 기본 설정을 해제할 수 없습니다.\n 해제하고 싶다면 다른 대문페이지 템플릿을 기본으로 설정하세요 ");
            }
        }
    }

    @Override
    public void validateForUpdateNaming(Long shopPageId, String naming) {
        checkConflictNaming(shopPageId, naming);

    }

    private void checkConflictNaming(Long shopPageId, String naming) {
        shopPageRepository.findByNaming(naming).ifPresent(shopPage -> {
            if (shopPageId == null) {
                throw new IllegalStateException("이미 존재하는 템플릿 별칭입니다.");
            }

            if (shopPage.getId() != shopPageId) {
                throw new IllegalStateException("이미 존재하는 템플릿 별칭입니다.");
            }
        });
    }

    @Override
    public void validateForUpdateSalePageIds(List<Long> salePageIds) {
        checkExistsSalePage(salePageIds);
    }

    @Override
    public void validateForUpdateIsDeleteAtEndOfEvent(Long shopPageId, boolean isDeleteAtEndOfEvent) {
        ShopPage shopPage = shopPageRepository.findById(shopPageId).orElseThrow(ShopPageNotFoundException::new);

        if (isDeleteAtEndOfEvent) {

            if (shopPage.isDefault()) {
                throw new IllegalStateException("기본 대문페이지 템플릿은 종료이벤트에대한 자동 삭제를 지원하지 않습니다.");
            }
        }

    }

    @Override
    public void validateForDelete(Long shopPageId) {
        ShopPage shopPage = shopPageRepository.findByIsDefault(true)
                .orElseThrow(() -> new IllegalStateException("현재 기본 대문페이지 템플릿이 없어 기본 템플릿부터 생성하십시오"));

        if (shopPage.getId() == shopPageId) {
            throw new IllegalArgumentException("기본 대문페이지 템플릿은 삭제할 수 없습니다.\n 삭제하고 싶다면 다른 대문페이지 템플릿을 기본으로 설정하세요 ");
        }
    }

    private void dateConflictChecker(LocalDateTime startedDateAt, LocalDateTime endedDateAt,
            List<ShopPage> all, Long exceptShopPageId) {

        if (startedDateAt == null && endedDateAt == null) {
            return;
        }

        if (startedDateAt == null) {
            throw new IllegalArgumentException("시작일을 입력해주세요");
        }

        if (endedDateAt == null) {
            throw new IllegalArgumentException("종료일을 입력해주세요");
        }

        if (startedDateAt.isAfter(endedDateAt)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
        if (endedDateAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("종료일은 현재 이후이어야 합니다.");
        }

        all.forEach(shopPage -> {

            Long shopPageId = shopPage.getId();
            if (exceptShopPageId != null && shopPageId == exceptShopPageId) {
                return;
            }

            LocalDateTime oldStartedDateAt = shopPage.getStartedDateAt();
            LocalDateTime oldEndedDateAt = shopPage.getEndedDateAt();

            if (oldStartedDateAt == null && oldEndedDateAt == null) {
                return;
            }

            String naming = shopPage.getNaming();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String errorMessage = "기존의 기간설정과 새로운 기간설정이 겹칩니다.\n" +
                    "겹치는 기존 템플릿 별칭 : " + naming + ", 번호 : " + String.valueOf(shopPageId) + "\n" +
                    "기존 기간 -> 시작일 : " + oldStartedDateAt.format(formatter) + ", 종료일 : "
                    + oldEndedDateAt.format(formatter) + "\n" +
                    "추가혀려는 템플릿의 기간 -> 시작일 : " + startedDateAt.format(formatter) + ", 종료일 : "
                    + endedDateAt.format(formatter)
                    + "\n";

            boolean isConflict = false;
            if (oldStartedDateAt.isBefore(startedDateAt) && oldEndedDateAt.isAfter(endedDateAt)) {
                isConflict = true;
            }

            else if (startedDateAt.isBefore(oldStartedDateAt) && endedDateAt.isAfter(oldEndedDateAt)) {
                isConflict = true;
            }

            else if (oldStartedDateAt.isBefore(startedDateAt) && oldEndedDateAt.isAfter(startedDateAt)) {
                isConflict = true;
            }

            else if (oldStartedDateAt.isBefore(endedDateAt) && oldEndedDateAt.isAfter(endedDateAt)) {
                isConflict = true;
            }

            if (isConflict) {
                throw new IllegalArgumentException(errorMessage);
            }

        });
    }

}
