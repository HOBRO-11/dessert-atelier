package com.yangnjo.dessert_atelier.service.total;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductDto;
import com.yangnjo.dessert_atelier.repository.total.dto.TotalSaleProductGraphDto;

public interface TotalSaleProductService {

    Page<TotalSaleProductDto> getByOptionId(Long productId, PageOption pageOption,
            PeriodOption periodOption);

    List<TotalSaleProductGraphDto> getGraphByProductId(Long productId, PeriodOption periodOption);
}
