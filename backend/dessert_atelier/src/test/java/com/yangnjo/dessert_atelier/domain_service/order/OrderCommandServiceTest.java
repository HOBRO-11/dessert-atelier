package com.yangnjo.dessert_atelier.domain_service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.delivery.Delivery;
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberRole;
import com.yangnjo.dessert_atelier.domain.order.OrderStatus;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain.value_type.Destination;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OrderCreateDto;
import com.yangnjo.dessert_atelier.domain_service.order.impl.OrderCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.MemberRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;

class OrderCommandServiceTest {

  @InjectMocks
  private OrderCommandServiceImpl orderCommandService;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUserOrder_성공() {
    // Given
    Long memberId = 1L;
    Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberRole.MEMBER, null);
    member.setIdToTest(memberId);

    Destination destination = new Destination("John Doe", "123-456-7890", "123 Main St", "12345");
    OrderCreateDto dto = new OrderCreateDto(memberId, null, destination, 100000L);

    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> {
      Orders savedOrder = invocation.getArgument(0);
      return savedOrder;
    });

    // When
    Long result = orderCommandService.createUserOrder(dto);

    // Then
    assertThat(result != null).isTrue();
    verify(memberRepository).findById(memberId);
    verify(orderRepository).save(any(Orders.class));
  }

  @Test
  void createGuestOrder_성공() {
    // Given
    Destination destination = new Destination("Jane Doe", "987-654-3210", "456 Elm St", "7890");
    OrderCreateDto dto = new OrderCreateDto(null, "guestpass", destination, 50000L);

    when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> {
      Orders savedOrder = invocation.getArgument(0);
      return savedOrder;
    });

    // When
    Long result = orderCommandService.createGuestOrder(dto);

    // Then
    assertThat(result != null).isTrue();
    verify(orderRepository).save(any(Orders.class));
  }

  @Test
  void updateOrderStatus_성공() {
    // Given
    Long orderCode = 20230101000003L;
    Orders order = Orders.createGuestOrder(orderCode, null, null, orderCode);
    order.setCodeToTest(orderCode);

    when(orderRepository.findById(orderCode)).thenReturn(Optional.of(order));

    // When
    orderCommandService.updateOrderStatus(orderCode, OrderStatus.COMPLETED);

    // Then
    assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
    verify(orderRepository).findById(orderCode);
  }

  @Test
  void setDelivery_성공() {
    // Given
    Long orderCode = 20230101000004L;
    Orders order = Orders.createGuestOrder(orderCode, null, null, orderCode);
    order.setCodeToTest(orderCode);
    DeliveryCompany deliveryCompany = new DeliveryCompany("Test Company", "123456789");
    Delivery delivery = new Delivery("DEL123", deliveryCompany);

    when(orderRepository.findById(orderCode)).thenReturn(Optional.of(order));

    // When
    orderCommandService.setDelivery(orderCode, delivery);

    // Then
    assertEquals(delivery, order.getDelivery());
    verify(orderRepository).findById(orderCode);
  }
}