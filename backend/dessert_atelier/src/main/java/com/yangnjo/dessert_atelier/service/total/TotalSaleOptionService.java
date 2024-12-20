package com.yangnjo.dessert_atelier.service.total;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleOptionGraphDto;

public interface TotalSaleOptionService {

    Page<TotalSaleOptionDto> getByOptionId(Long optionId, PageOption pageOption,
            PeriodOption periodOption);

    List<TotalSaleOptionGraphDto> getGraphByOptionId(Long optionId, PeriodOption periodOption);
}
