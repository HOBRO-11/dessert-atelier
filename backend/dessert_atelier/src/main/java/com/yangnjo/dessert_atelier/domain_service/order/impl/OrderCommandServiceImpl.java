package com.yangnjo.dessert_atelier.domain_service.order.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.OrderCommandService;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OrderCreateDto;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OrderCodeGenerateFailedException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OrderNotFoundException;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private static final int RANDOM_NUM_MAX_LENGTH = 6;
    private static final int GENERATE_ORDER_CODE_MAX_COUNT = 3;

    @Override
    public Long createUserOrder(final OrderCreateDto dto) {
        Member member = findMemberById(dto.getMemberId());
        Long orderCode = generateOrderCode();
        Orders order = dto.toUserOrderEntity(orderCode, member);
        Orders savedOrder = orderRepository.save(order);
        return savedOrder.getOrderCode();
    }

    @Override
    public Long createGuestOrder(final OrderCreateDto dto) {
        Long orderCode = generateOrderCode();
        Orders order = dto.toGuestOrderEntity(orderCode);
        Orders savedOrder = orderRepository.save(order);
        return savedOrder.getOrderCode();
    }

    @Override
    public void updateOrderStatus(Long orderCode, OrderStatus status) {
        Orders order = findOrderByCode(orderCode);
        order.setOrderStatus(status);
    }

    @Override
    public void setDelivery(Long orderCode, Delivery delivery) {
        Orders orders = findOrderByCode(orderCode);
        orders.setDelivery(delivery);
    }

    private Orders findOrderByCode(Long orderCode) {
        Orders order = orderRepository.findById(orderCode)
                .orElseThrow(() -> new OrderNotFoundException());
        return order;
    }

    private Member findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());
        return member;
    }

    private Long generateOrderCode() {
        String orderDatePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long orderCode = 0L;
        int count = 0;

        while (count++ < GENERATE_ORDER_CODE_MAX_COUNT) {
            String randomNum = String.valueOf(System.nanoTime() % 1_000_000);
            StringBuilder sb = new StringBuilder(orderDatePrefix);

            if (randomNum.length() < RANDOM_NUM_MAX_LENGTH) {
                sb.append("0");
                sb.append(randomNum);
            } else {
                sb.append(randomNum);
            }

            orderCode = Long.valueOf(sb.toString());

            if (isOrderCodeNonExists(orderCode) == false) {
                return orderCode;
            }

            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                throw new OrderCodeGenerateFailedException();
            }
        }

        throw new OrderCodeGenerateFailedException();
    }

    private boolean isOrderCodeNonExists(Long orderCode) {
        return orderRepository.existsByOrderCode(orderCode);
    }

}
