package com.yangnjo.dessert_atelier.domain_service.delivery;

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
import com.yangnjo.dessert_atelier.domain.delivery.DeliveryStatus;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.impl.DeliveryCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;
import com.yangnjo.dessert_atelier.repository.DeliveryRepository;

public class DeliveryCommandServiceTest {

    @InjectMocks
    private DeliveryCommandServiceImpl deliveryCommandService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryCompanyRepository deliveryCompanyRepository;

    private DeliveryCompany deliveryCompany;
    private DeliveryCreateDto deliveryCreateDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deliveryCompany = new DeliveryCompany("Test Company", "123456789");
        deliveryCompany.setIdToTest(1L);
        deliveryCreateDto = new DeliveryCreateDto("DEL123", 1L);
    }

    @Test
    public void testCreateDelivery() {
      Delivery delivery = new Delivery("DEL123", deliveryCompany);
      delivery.setIdToTest(1L);

        when(deliveryCompanyRepository.findById(1L)).thenReturn(Optional.of(deliveryCompany));
        when(deliveryRepository.save(any(Delivery.class))).thenReturn(delivery);

        Long deliveryId = deliveryCommandService.createDelivery(deliveryCreateDto);

        assertNotNull(deliveryId);
        verify(deliveryCompanyRepository).findById(1L);
        verify(deliveryRepository).save(any(Delivery.class));
    }

    @Test
    public void testUpdateDeliveryStatus() {
        Delivery delivery = new Delivery("DEL123", deliveryCompany);
        delivery.setIdToTest(1L);
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));

        deliveryCommandService.updateDeliveryStatus(1L, DeliveryStatus.COMPLETED);

        assertEquals(DeliveryStatus.COMPLETED, delivery.getDeliveryStatus());
        verify(deliveryRepository).findById(1L);
    }

    @Test
    public void testDeleteDelivery() {
        Delivery delivery = new Delivery("DEL123", deliveryCompany);
        delivery.setIdToTest(1L);
        when(deliveryRepository.findById(1L)).thenReturn(Optional.of(delivery));

        deliveryCommandService.deleteDelivery(1L);

        verify(deliveryRepository).deleteById(1L);
    }

}
