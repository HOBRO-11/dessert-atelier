package com.yangnjo.dessert_atelier.sale.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.sale.domain.entity.ShopPage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShopPageCreateDto {

    private String naming;
    private List<Long> salePageIds;
    private boolean isDefault;
    private LocalDateTime startedDateAt;
    private LocalDateTime endedDateAt;
    private boolean isDeleteAtEndOfEvent;

    public ShopPage toEntity() {
        return ShopPage.builder()
                .naming(naming)
                .salePageIds(salePageIds)
                .isDefault(isDefault)
                .startedDateAt(startedDateAt)
                .endedDateAt(endedDateAt)
                .isDeleteAtEndOfEvent(isDeleteAtEndOfEvent)
                .build();
    }
}
