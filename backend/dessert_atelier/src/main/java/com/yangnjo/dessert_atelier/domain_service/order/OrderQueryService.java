package com.yangnjo.dessert_atelier.domain_service.order;

import java.util.List;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.OrderDestinationDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderSimpleDto;

public interface OrderQueryService {

  List<OrderSimpleDto> getSimpleOrdersByMemberId(Long memberId, PageOption pageOption,
      PeriodOption periodOption);

  OrderDetailDto getOrderDetailByOrderCode(Long orderCode);

  OrderDestinationDto getOrderDestinationByOrderCode(Long orderCode);

}