package com.yangnjo.dessert_atelier.service.basket.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponseFrom {
    private List<BasketPropertyDetail> basketPropertyDetails = new ArrayList<>();

    public static BasketPropertyDetail create(BasketProperty prop, Map<Long, OptionSimpleDto> osds,
            Map<Long, DpSimpleDto> dpSimpleMap,
            boolean isAvailable) {
        return new BasketPropertyDetail(prop, osds, dpSimpleMap, isAvailable);
    }

    @Getter
    public static class BasketPropertyDetail {
        private Long displayProductId;
        private String displayProductTitle;
        private String dpThumb;
        private List<OptionDetail> optionDetails;
        private Integer quantity;
        private LocalDateTime updatedAt;
        private boolean isAvailable;

        public BasketPropertyDetail(BasketProperty prop, Map<Long, OptionSimpleDto> osds,
                Map<Long, DpSimpleDto> dpSimpleMap,
                boolean isAvailable) {
            List<OptionDetail> arrayList = new ArrayList<>();

            prop.getOptionIds().forEach(optionId -> {
                OptionSimpleDto osd = osds.get(optionId);
                arrayList.add(new OptionDetail(osd));
            });

            DpSimpleDto dpDto = dpSimpleMap.get(getDpId(osds));

            this.displayProductId = dpDto.getId();
            this.displayProductTitle = dpDto.getTitle();
            this.dpThumb = dpDto.getThumb();
            this.optionDetails = arrayList;
            this.quantity = prop.getQuantity();
            this.updatedAt = prop.getUpdatedAt();
            this.isAvailable = isAvailable;
        }

        private Long getDpId(Map<Long, OptionSimpleDto> osds) {
            return osds.entrySet().iterator().next().getValue().getDisplayProductId();
        }

    }

    @Getter
    public static class OptionDetail {
        private Long optionId;
        private String description;
        private OptionStatus optionStatus;
        private Integer optionLayer;
        private Integer price;

        public OptionDetail(OptionSimpleDto osd) {
            this.optionId = osd.getId();
            this.description = osd.getDescription();
            this.optionStatus = osd.getOptionStatus();
            this.optionLayer = osd.getOptionLayer();
            this.price = osd.getPrice();
        }

    }

}
