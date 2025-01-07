package com.yangnjo.dessert_atelier.repository.order.query.query_dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PeriodOption;
import com.yangnjo.dessert_atelier.domain_model.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain_model.order.OrderedOption;
import com.yangnjo.dessert_atelier.domain_model.order.OrderedOptionStatus;
import com.yangnjo.dessert_atelier.domain_model.order.QOrders;
import com.yangnjo.dessert_atelier.domain_model.product.QProductOption;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderDto;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderDto.OrderedOptionDto;
import com.yangnjo.dessert_atelier.repository.order.dto.OrderSimpleDto;
import com.yangnjo.dessert_atelier.repository.order.query.OrderQueryRepo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepoImpl implements OrderQueryRepo {
    private final JPAQueryFactory queryFactory;
    QOrders orders = QOrders.orders;
    QProductOption productOption = QProductOption.productOption;

    @Override
    public Optional<OrderDto> findByOrderCode(Long orderCode) {
        OrderDto dto = queryFactory
                .select(OrderDto.asIncompleteDto())
                .from(orders)
                .where(equalOrderCode(orderCode))
                .fetchOne();

        if (dto == null) {
            return Optional.empty();
        }

        List<OrderedOption> orderedOptions = dto.getOrderedOptions();
        List<OrderedOptionDto> opDtos = new ArrayList<>();

        for (OrderedOption orderedOption : orderedOptions) {
            List<Long> optionIds = orderedOption.getOptionIds();
            String title = getTitle(optionIds);
            List<String> desc = getDesc(optionIds);
            Integer quantity = orderedOption.getQuantity();
            OrderedOptionStatus status = orderedOption.getStatus();
            opDtos.add(new OrderedOptionDto(title, desc, status, quantity));
        }

        dto.setOrderedOptionDtos(opDtos);
        return Optional.of(dto);
    }

    @Override
    public List<OrderSimpleDto> findAllSimpleByOrderStatus(OrderStatus orderStatus, PageOption pageOption,
            PeriodOption periodOption) {
        List<OrderSimpleDto> dtos = queryFactory.select(OrderSimpleDto.asIncompleteDto())
                .from(orders)
                .where(equalOrderStatus(orderStatus), PeriodOption.betweenLDT(orders.createdAt, periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(orders.orderCode))
                .fetch();

        setDpTitleAtDtos(dtos);

        return dtos;
    }

    @Override
    public List<OrderSimpleDto> findAllSimpleByMemberId(Long memberId, PageOption pageOption,
            PeriodOption periodOption) {
        List<OrderSimpleDto> dtos = queryFactory.select(OrderSimpleDto.asIncompleteDto())
                .from(orders)
                .where(equalMemberId(memberId), PeriodOption.betweenLDT(orders.createdAt, periodOption))
                .offset(pageOption.getOffset())
                .limit(pageOption.getSize())
                .orderBy(pageOption.getDirection(orders.orderCode))
                .fetch();

        setDpTitleAtDtos(dtos);

        return dtos;
    }

    @Override
    public Long countAllSimpleByOrderStatus(OrderStatus orderStatus) {
        return queryFactory.select(orders.count())
                .from(orders)
                .where(equalOrderStatus(orderStatus))
                .fetchOne();
    }

    @Override
    public Long countAllSimpleByMemberId(Long memberId) {
        return queryFactory.select(orders.count())
                .from(orders)
                .where(equalMemberId(memberId))
                .fetchOne();
    }

    private void setDpTitleAtDtos(List<OrderSimpleDto> dtos) {
        Set<Long> optionFirstIds = dtos.stream().map(dto -> {
            Long optionFirstId = dto.getOrderedOptions().get(0).getOptionIds().get(0);
            return optionFirstId;
        }).collect(Collectors.toSet());

        Map<Long, String> optionIdAndDpTitle = queryFactory.select(productOption.id, productOption.productOptionHeader.displayProduct.title)
                .from(productOption)
                .where(productOption.id.in(optionFirstIds))
                .fetch()
                .stream()
                .collect(Collectors.toMap(tuple -> tuple.get(productOption.id),
                        tuple -> tuple.get(productOption.productOptionHeader.displayProduct.title)));

        dtos.stream().forEach(dto -> {
            Long optionFirstId = dto.getOrderedOptions().get(0).getOptionIds().get(0);
            String dpTitle = optionIdAndDpTitle.get(optionFirstId);
            if (dpTitle == null) {
                dpTitle = "현재 판매 중인 상품이 없습니다.";
            }
            dto.setDpTitle(dpTitle);
        });
    }

    private BooleanExpression equalOrderStatus(OrderStatus orderStatus) {
        return orders.orderStatus.eq(orderStatus);
    }

    private BooleanExpression equalOrderCode(Long orderCode) {
        return orders.orderCode.eq(orderCode);
    }

    private List<String> getDesc(List<Long> optionIds) {
        return queryFactory.select(productOption.description)
                .from(productOption)
                .where(productOption.id.in(optionIds))
                .fetch();
    }

    private String getTitle(List<Long> optionIds) {
        Long firstOptionId = optionIds.get(0);
        return queryFactory.select(productOption.productOptionHeader.displayProduct.title)
                .from(productOption)
                .where(productOption.id.eq(firstOptionId))
                .fetchOne();
    }

    private BooleanExpression equalMemberId(Long memberId) {
        return orders.member.id.eq(memberId);
    }

}
