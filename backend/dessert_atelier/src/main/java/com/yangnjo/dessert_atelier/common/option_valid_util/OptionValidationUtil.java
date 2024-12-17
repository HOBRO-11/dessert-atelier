package com.yangnjo.dessert_atelier.common.option_valid_util;

import java.util.List;
import java.util.Map;

import com.yangnjo.dessert_atelier.domain_model.product.OptionStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.repository.product.dto.OptionSimpleDto;

public class OptionValidationUtil {

    /*
     * 옵션의 유효성 평가 기준
     * 
     * 1. option의 layer는 1부터 시작한다.
     * 1. basketProperty에 속한 옵션의 layer는 연속되며, 가장 높은 layer는 dp의 layer와 같아야한다.
     * 2. 옵션의 상태에 따라 유효성이 달라진다.
     * 3. preset table에 존재하는 dp이어야지만 유효하다.
     */

    public static boolean isAvailable(List<Long> optionIds, Map<Long, OptionSimpleDto> optionSimpleMap,
            Map<Long, DpSimpleDto> DpSimpleMap,
            List<Long> presetTableDpIds) {

        if (optionIds == null || optionIds.isEmpty()) {
            return false;
        }

        if (optionSimpleMap == null || optionSimpleMap.isEmpty()) {
            return false;
        }

        if (DpSimpleMap == null || DpSimpleMap.isEmpty()) {
            return false;
        }

        if (presetTableDpIds == null || presetTableDpIds.isEmpty()) {
            return false;
        }

        int dpOptionLayer = getDpOptionLayer(optionIds, optionSimpleMap, DpSimpleMap);
        Long dpId = getDpId(optionIds, optionSimpleMap);

        if (dpId == null) {
            return false;
        }

        if (presetTableDpIds.contains(dpId) == false) {
            return false;
        }

        int layerCnt = 1;

        for (Long optionId : optionIds) {
            if (layerCnt > dpOptionLayer) {
                return false;
            }
            OptionSimpleDto osd = optionSimpleMap.get(optionId);
            if (osd == null) {
                return false;
            }
            if (osd.getOptionStatus() != OptionStatus.AVAILABLE) {
                return false;
            }
            if (osd.getDisplayProductId() != dpId) {
                return false;
            }
            if (layerCnt != osd.getOptionLayer()) {
                return false;
            }
            if (osd.getOptionLayer() > dpOptionLayer && dpOptionLayer < 0) {
                return false;
            }
            layerCnt++;
        }

        if (layerCnt != dpOptionLayer + 1) {
            return false;
        }

        return true;
    }

    public static void checkValid(List<Long> optionIds, Map<Long, OptionSimpleDto> optionSimpleMap,
            Map<Long, DpSimpleDto> DpSimpleMap,
            List<Long> presetTableDpIds) {

        if (optionIds == null || optionIds.isEmpty()) {
            throw new OptionValidateException("현재 판매하지 않는 옵션이 포함되어있습니다.");
        }

        if (optionSimpleMap == null || optionSimpleMap.isEmpty()) {
            throw new OptionValidateException("현재 판매하지 않는 옵션이 포함되어있습니다.");
        }

        if (DpSimpleMap == null || DpSimpleMap.isEmpty()) {
            throw new OptionValidateException("현재 판매하지 않는 상품입니니다.");
        }

        if (presetTableDpIds == null || presetTableDpIds.isEmpty()) {
            throw new OptionValidateException("현재 판매하지 않는 상품입니니다.");
        }

        int dpOptionLayer = getDpOptionLayer(optionIds, optionSimpleMap, DpSimpleMap);
        Long dpId = getDpId(optionIds, optionSimpleMap);

        if (dpId == null) {
            throw new OptionValidateException("현재 판매하지 않는 상품있니다.");
        }

        if (presetTableDpIds.contains(dpId) == false) {
            throw new OptionValidateException("현재 판매하지 않는 상품있니다.");
        }

        int layerCnt = 1;

        for (Long optionId : optionIds) {
            if (layerCnt > dpOptionLayer) {
                throw new OptionValidateException("현재 판매하지 않는 옵션이 포함되어있습니다.");
            }
            OptionSimpleDto osd = optionSimpleMap.get(optionId);
            if (osd == null) {
                throw new OptionValidateException("존재하지 않는 옵션이 포함되어있습니다.");
            }
            String description = osd.getDescription();
            if (osd.getOptionStatus() != OptionStatus.AVAILABLE) {
                throw new OptionValidateException("현재 판매하지 않는 옵션이 포함되어있습니다. : " + description);
            }
            if (osd.getDisplayProductId() != dpId) {
                throw new OptionValidateException("해당 상품에 없는 옵션이 포함되어있습니다. : " + description);
            }
            if (layerCnt != osd.getOptionLayer()) {
                throw new OptionValidateException("올바르지 않은 옵션이 포함되어있습니다. : " + description);
            }
            if (osd.getOptionLayer() > dpOptionLayer && dpOptionLayer < 0) {
                throw new OptionValidateException("해당 상품에 없는 옵션이 포함되어있습니다. : " + description);
            }
            layerCnt++;
        }

        if (layerCnt != dpOptionLayer + 1) {
            throw new OptionValidateException("현재 판매하지 않는 옵션이 포함되어있습니다.");
        }

        return;
    }

    private static Long getDpId(List<Long> optionIds, Map<Long, OptionSimpleDto> osds) {
        return osds.get(optionIds.get(0)).getDisplayProductId();
    }

    private static Integer getDpOptionLayer(List<Long> optionIds, Map<Long, OptionSimpleDto> osds,
            Map<Long, DpSimpleDto> dsdMap) {
        return dsdMap.get(osds.get(optionIds.get(0)).getDisplayProductId()).getOptionLayer();
    }

}
