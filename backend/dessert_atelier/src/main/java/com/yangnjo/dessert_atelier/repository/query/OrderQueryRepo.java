package com.yangnjo.dessert_atelier.repository.query;

import java.util.List;
import java.util.Optional;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.repository.dto.OrderDestinationDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderDetailDto;
import com.yangnjo.dessert_atelier.repository.dto.OrderSimpleDto;

public interface OrderQueryRepo {

  List<OrderSimpleDto> findSimpleByMemberId(Long memberId, PageOption pageOption, PeriodOption periodOption);

  Optional<OrderDetailDto> findDetailByOrderCode(Long orderCode);

  Optional<OrderDestinationDto> findDestinationByOrderCode(Long orderCode);

}