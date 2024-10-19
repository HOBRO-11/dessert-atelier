package com.yangnjo.dessert_atelier.domain_service.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.member.Member;
import com.yangnjo.dessert_atelier.domain.member.MemberOrigin;
import com.yangnjo.dessert_atelier.domain.order.Basket;
import com.yangnjo.dessert_atelier.domain.order.BasketProperty;
import com.yangnjo.dessert_atelier.domain_service.member.exception.MemberNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketOverMaxCountException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.BasketPropertyNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.impl.BasketCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.BasketRepository;
import com.yangnjo.dessert_atelier.repository.MemberRepository;

class BasketCommandServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private BasketCommandServiceImpl basketCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBasket_성공() {
        // Given
        Long memberId = 1L;
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);
        Basket basket = new Basket(member);
        basket.setIdToTest(1L);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        // When
        Long result = basketCommandService.createBasket(memberId);

        // Then
        assertEquals(1L, result);
        verify(memberRepository).findById(memberId);
        verify(basketRepository).save(any(Basket.class));
    }

    @Test
    void createBasket_회원_없음() {
        // Given
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(MemberNotFoundException.class, () -> basketCommandService.createBasket(memberId));
    }

    @Test
    void addProperties_성공() {
        // Given
        Long memberId = 1L;
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);

        Basket basket = new Basket(member);
        member.setBasket(basket);
        List<BasketProperty> properties = new ArrayList<>();
        properties.add(new BasketProperty(1L, Arrays.asList(1L, 2L)));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When
        basketCommandService.addProperties(memberId, properties);

        // Then
        assertEquals(1, basket.getProperties().size());
        verify(memberRepository).findById(memberId);
    }

    @Test
    void addProperties_장바구니_없음() {
        // Given
        Long memberId = 1L;
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);
        List<BasketProperty> properties = new ArrayList<>();

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When & Then
        assertThrows(BasketNotFoundException.class, () -> basketCommandService.addProperties(memberId, properties));
    }

    @Test
    void addProperties_장바구니_최대_개수_초과() {
        // Given
        Long memberId = 1L;
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);
        Basket basket = new Basket(member);
        member.setBasket(basket);
        List<BasketProperty> existingProperties = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            existingProperties.add(new BasketProperty(Long.valueOf(i), Arrays.asList(1L)));
        }
        basket.getProperties().addAll(existingProperties);

        List<BasketProperty> newProperties = new ArrayList<>();
        newProperties.add(new BasketProperty(21L, Arrays.asList(1L)));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When & Then
        assertThrows(BasketOverMaxCountException.class, () -> basketCommandService.addProperties(memberId, newProperties));
    }

    @Test
    void removeProperty_성공() {
        // Given
        Long memberId = 1L;
        Long dppId = 1L;
        List<Long> optionIds = Arrays.asList(1L, 2L);
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);
        Basket basket = new Basket(member);
        member.setBasket(basket);
        basket.addProperty(new BasketProperty(dppId, optionIds));

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When
        basketCommandService.removeProperty(memberId, dppId, optionIds);

        // Then
        assertTrue(basket.getProperties().isEmpty());
        verify(memberRepository).findById(memberId);
    }

    @Test
    void removeProperty_장바구니_없음() {
        // Given
        Long memberId = 1L;
        Long dppId = 1L;
        List<Long> optionIds = Arrays.asList(1L, 2L);
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When & Then
        assertThrows(BasketNotFoundException.class, () -> basketCommandService.removeProperty(memberId, dppId, optionIds));
    }

    @Test
    void removeProperty_장바구니_속성_없음() {
        // Given
        Long memberId = 1L;
        Long dppId = 1L;
        List<Long> optionIds = Arrays.asList(1L, 2L);
        Member member = new Member("Test User", "1234567890", "Test User", "1234567890", MemberOrigin.STORE);
        Basket basket = new Basket(member);
        member.setBasket(basket);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // When & Then
        assertThrows(BasketPropertyNotFoundException.class, () -> basketCommandService.removeProperty(memberId, dppId, optionIds));
    }
}