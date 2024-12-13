package com.yangnjo.dessert_atelier.service.basket.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.yangnjo.dessert_atelier.common.option_valid_util.OptionValidationUtil;
import com.yangnjo.dessert_atelier.domain_model.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_service.order.BasketCommandService;
import com.yangnjo.dessert_atelier.domain_service.order.BasketQueryService;
import com.yangnjo.dessert_atelier.domain_service.product.DisplayProductQueryService;
import com.yangnjo.dessert_atelier.domain_service.product.OptionQueryService;
import com.yangnjo.dessert_atelier.domain_service.product.PresetTableQueryService;
import com.yangnjo.dessert_atelier.repository.order.dto.BasketDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;
import com.yangnjo.dessert_atelier.service.basket.BasketService;
import com.yangnjo.dessert_atelier.service.basket.dto.BasketAddForm;
import com.yangnjo.dessert_atelier.service.basket.dto.BasketRemoveForm;
import com.yangnjo.dessert_atelier.service.basket.dto.BasketResponseFrom;
import com.yangnjo.dessert_atelier.service.basket.dto.BasketResponseFrom.BasketPropertyDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketCommandService basketCommandService;
    private final BasketQueryService basketQueryService;
    private final OptionQueryService optionQueryService;
    private final DisplayProductQueryService displayProductQueryService;
    private final PresetTableQueryService presetTableQueryService;

    @Override
    public BasketResponseFrom getBasket(Long memberId) {
        BasketDto basketDto = getBasketDto(memberId);

        if (basketDto == null) {
            return new BasketResponseFrom();
        }

        List<BasketProperty> props = basketDto.getProperties();
        Map<Long, OptionSimpleDto> optionMap = getOptionSimpleLeftJoinMap(props);
        Map<Long, DpSimpleDto> DpMap = getDpSimpleLeftJoinMap(optionMap);
        List<Long> presetTableDpIds = getPresetTableDpId();

        if (optionMap == null || DpMap == null || presetTableDpIds == null) {
            return new BasketResponseFrom();
        }

        List<BasketPropertyDetail> dpds = getBasketPropertyDetails(props, optionMap, DpMap, presetTableDpIds);
        return new BasketResponseFrom(dpds);
    }

    /**
     * @param memberId
     * @param form
     * @throw OptionValidateException
     */
    @Override
    public void addBasket(Long memberId, BasketAddForm form) {
        List<BasketProperty> props = form.getBasketProperties();

        Map<Long, OptionSimpleDto> optionMap = getOptionSimpleLeftJoinMap(props);
        Map<Long, DpSimpleDto> DpMap = getDpSimpleLeftJoinMap(optionMap);

        checkBasketProperties(props, optionMap, DpMap);

        basketCommandService.addProperties(memberId, props);
    }

    @Override
    public void removeBasket(Long memberId, BasketRemoveForm form) {
        List<BasketProperty> props = form.getBasketProperties();

        if (props == null) {
            return;
        }

        props.forEach(prop -> basketCommandService.removeProperty(memberId, prop.getOptionIds()));
    }

    /**
     * @param props
     * @param optionMap
     * @param DpMap
     * @throw OptionValidateException
     */
    private void checkBasketProperties(List<BasketProperty> props, Map<Long, OptionSimpleDto> optionMap,
            Map<Long, DpSimpleDto> DpMap) {
        props.forEach(prop -> checkOptions(prop.getOptionIds(), optionMap, DpMap, getPresetTableDpId()));
    }

    private List<BasketPropertyDetail> getBasketPropertyDetails(List<BasketProperty> props,
            Map<Long, OptionSimpleDto> optionMap,
            Map<Long, DpSimpleDto> DpMap, List<Long> presetTableDpIds) {
        return props.stream().map(prop -> {
            boolean available = isAvailable(prop.getOptionIds(), optionMap, DpMap,
                    presetTableDpIds);
            return BasketResponseFrom.create(prop, optionMap, DpMap, available);
        }).collect(Collectors.toList());
    }

    /**
     * @param osljm
     * @return Map<Long, DpSimpleDto> , 과정중에 null 또는 empty가 되어있을 경우 null을 반환한다.
     */
    private Map<Long, DpSimpleDto> getDpSimpleLeftJoinMap(Map<Long, OptionSimpleDto> osljm) {
        if (osljm == null || osljm.isEmpty()) {
            return null;
        }

        List<Long> dpIds = osljm.entrySet().stream()
                .filter(Objects::nonNull)
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getValue().getDisplayProductId())
                .distinct()
                .collect(Collectors.toList());

        if (dpIds.isEmpty()) {
            return null;
        }

        Map<Long, DpSimpleDto> dpSimpleMap = displayProductQueryService.getAllSimpleByIdIn(dpIds).stream()
                .filter(dto -> dto != null)
                .collect(Collectors.toMap(DpSimpleDto::getId, dto -> dto));

        if (dpSimpleMap.isEmpty()) {
            return null;
        }

        Map<Long, DpSimpleDto> result = new HashMap<>();
        dpIds.forEach(dpId -> result.put(dpId, dpSimpleMap.get(dpId)));
        return result;
    }

    /**
     * @param props
     * @return Map<Long, OptionSimpleDto> , 과정중에 null 또는 empty가 되어있을 경우 null을 반환한다.
     */
    private Map<Long, OptionSimpleDto> getOptionSimpleLeftJoinMap(List<BasketProperty> props) {
        if (props == null || props.isEmpty()) {
            return null;
        }

        List<Long> optionIds = props.stream()
                .filter(prop -> prop != null)
                .flatMap(prop -> prop.getOptionIds().stream())
                .distinct()
                .collect(Collectors.toList());

        if (optionIds.isEmpty()) {
            return null;
        }

        Map<Long, OptionSimpleDto> optionSimpleMap = optionQueryService.getAllSimpleByOptionIdIn(optionIds).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(OptionSimpleDto::getId, dto -> dto));

        if (optionSimpleMap.isEmpty()) {
            return null;
        }

        Map<Long, OptionSimpleDto> result = new HashMap<>();
        optionIds.forEach(optionId -> result.put(optionId, optionSimpleMap.get(optionId)));
        return result;
    }

    private List<Long> getPresetTableDpId() {
        return presetTableQueryService.getAllPresetTables().stream()
                .filter(Objects::nonNull)
                .map(ptd -> ptd.getDpId())
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isAvailable(List<Long> optionIds, Map<Long, OptionSimpleDto> optionSimpleMap,
            Map<Long, DpSimpleDto> DpSimpleMap,
            List<Long> presetTableDpIds) {
        return OptionValidationUtil.isAvailable(optionIds, optionSimpleMap, DpSimpleMap, presetTableDpIds);
    }

    
    
    /** 
     * @param optionIds
     * @param optionSimpleMap
     * @param DpSimpleMap
     * @param availableDpId
     * @throw OptionValidateException
     */
    private void checkOptions(List<Long> optionIds, Map<Long, OptionSimpleDto> optionSimpleMap,
            Map<Long, DpSimpleDto> DpSimpleMap,
            List<Long> availableDpId) {
        OptionValidationUtil.checkValid(optionIds, optionSimpleMap, DpSimpleMap, availableDpId);
    }

    /**
     * @param memberId
     * @return BasketDto, 결과가 없을 때 null을 반환한다.
     */
    private BasketDto getBasketDto(Long memberId) {
        return basketQueryService.getByMemberId(memberId).orElse(null);
    }

}
