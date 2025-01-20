package com.yangnjo.dessert_atelier.sale.domain.domain_service;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.sale.dto.ShopPageCreateDto;

public interface ShopPageService {

    Long create(ShopPageCreateDto dto);

    void updateIsDefault(Long shopPageId, boolean isDefault);

    void updateNaming(Long shopPageId, String naming);

    void updateSalePageIds(Long shopPageId, List<Long> salePageIds);

    void updateIsDeleteAtEndOfEvent(Long shopPageId, boolean isDeleteAtEndOfEvent);

    void updateDuration(Long presetTableId, LocalDateTime startedDateAt, LocalDateTime endedDateAt);

    void delete(Long presetTableId);

}