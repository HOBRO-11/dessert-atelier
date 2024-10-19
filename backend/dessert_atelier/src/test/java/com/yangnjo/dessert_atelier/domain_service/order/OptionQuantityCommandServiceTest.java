package com.yangnjo.dessert_atelier.domain_service.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantity;
import com.yangnjo.dessert_atelier.domain.order.OptionQuantityStatus;
import com.yangnjo.dessert_atelier.domain.order.Orders;
import com.yangnjo.dessert_atelier.domain_service.order.dto.OptionQuantityCreateDto;
import com.yangnjo.dessert_atelier.domain_service.order.exception.DppNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OptionQuantityNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.exception.OrderNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.order.impl.OqCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.OqRepository;
import com.yangnjo.dessert_atelier.repository.OrderRepository;

class OptionQuantityCommandServiceTest {

    @InjectMocks
    private OqCommandServiceImpl optionQuantityCommandService;

    @Mock
    private OqRepository oqRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DisplayProductPresetRepository displayProductPresetRepository;

    @Mock
    private OptionRepository optionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOptionQuantity_Success() {
        // Given
        Long orderCode = 1L;
        Long dppId = 2L;
        List<Long> optionIds = Arrays.asList(3L, 4L);
        Integer quantity = 2;
        OptionQuantityCreateDto dto = new OptionQuantityCreateDto(orderCode, dppId, optionIds, quantity);

        Orders mockOrder = mock(Orders.class);
        DisplayProductPreset mockDpp = mock(DisplayProductPreset.class);
        List<Option> mockOptions = Arrays.asList(mock(Option.class), mock(Option.class));
        OptionQuantity mockOq = mock(OptionQuantity.class);

        when(orderRepository.findById(orderCode)).thenReturn(Optional.of(mockOrder));
        when(displayProductPresetRepository.findById(dppId)).thenReturn(Optional.of(mockDpp));
        when(optionRepository.findAllById(optionIds)).thenReturn(mockOptions);
        when(oqRepository.save(any(OptionQuantity.class))).thenReturn(mockOq);
        when(mockOq.getId()).thenReturn(5L);

        // When
        Long result = optionQuantityCommandService.createOptionQuantity(dto);

        // Then
        assertEquals(5L, result);
        verify(oqRepository).save(any(OptionQuantity.class));
    }

    @Test
    void createOptionQuantity_OrderNotFound() {
        // Given
        Long orderCode = 1L;
        OptionQuantityCreateDto dto = new OptionQuantityCreateDto(orderCode, 2L, Arrays.asList(3L, 4L), 2);

        when(orderRepository.findById(orderCode)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(OrderNotFoundException.class, () -> optionQuantityCommandService.createOptionQuantity(dto));
    }

    @Test
    void createOptionQuantity_DppNotFound() {
        // Given
        Long orderCode = 1L;
        Long dppId = 2L;
        OptionQuantityCreateDto dto = new OptionQuantityCreateDto(orderCode, dppId, Arrays.asList(3L, 4L), 2);

        when(orderRepository.findById(orderCode)).thenReturn(Optional.of(mock(Orders.class)));
        when(displayProductPresetRepository.findById(dppId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(DppNotFoundException.class, () -> optionQuantityCommandService.createOptionQuantity(dto));
    }

    @Test
    void updateOptionQuantityStatus_Success() {
        // Given
        Long oqId = 1L;
        OptionQuantityStatus newStatus = OptionQuantityStatus.CANCELLED;
        OptionQuantity mockOq = mock(OptionQuantity.class);

        when(oqRepository.findById(oqId)).thenReturn(Optional.of(mockOq));

        // When
        optionQuantityCommandService.updateOptionQuantityStatus(oqId, newStatus);

        // Then
        verify(mockOq).setStatus(newStatus);
    }

    @Test
    void updateOptionQuantityStatus_OptionQuantityNotFound() {
        // Given
        Long oqId = 1L;
        OptionQuantityStatus newStatus = OptionQuantityStatus.CANCELLED;

        when(oqRepository.findById(oqId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(OptionQuantityNotFoundException.class, () -> optionQuantityCommandService.updateOptionQuantityStatus(oqId, newStatus));
    }

    @Test
    void deleteOptionQuantity() {
        // Given
        Long oqId = 1L;

        // When
        optionQuantityCommandService.deleteOptionQuantity(oqId);

        // Then
        verify(oqRepository).deleteById(oqId);
    }
}