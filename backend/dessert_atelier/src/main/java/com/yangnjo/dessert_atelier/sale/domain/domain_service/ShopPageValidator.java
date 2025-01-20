package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.ShopPageCreateDto;

public interface ShopPageValidator {

    void validateForCreate(ShopPageCreateDto dto);

    void validateForUpdateDate(Long shopPageId, LocalDateTime startedDateAt, LocalDateTime endedDateAt);

    void validateForUpdateDefault(Long shopPageId, boolean isDefault);

    void validateForUpdateSalePageIds(List<Long> salePageIds);

    void validateForUpdateNaming(Long shopPageId, String naming);

    void validateForUpdateIsDeleteAtEndOfEvent(Long shopPageId, boolean isDeleteAtEndOfEvent);

    void validateForDelete(Long shopPageId);

}