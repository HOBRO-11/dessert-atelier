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

import com.yangnjo.dessert_atelier.domain.delivery.DeliveryCompany;
import com.yangnjo.dessert_atelier.domain_service.delivery.dto.DeliveryCompanyCreateDto;
import com.yangnjo.dessert_atelier.domain_service.delivery.impl.DcCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DeliveryCompanyRepository;

public class DeliveryCompanyCommandServiceTest {

    @InjectMocks
    private DcCommandServiceImpl deliveryCompanyCommandService;

    @Mock
    private DeliveryCompanyRepository deliveryCompanyRepo;

    private DeliveryCompany deliveryCompany;
    private DeliveryCompanyCreateDto deliveryCompanyCreateDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deliveryCompany = new DeliveryCompany("Test Company", "123456789");
        deliveryCompany.setIdToTest(1L);
        deliveryCompanyCreateDto = new DeliveryCompanyCreateDto("Test Company", "123456789");
    }

    @Test
    public void testCreateDeliveryCompany() {
        when(deliveryCompanyRepo.save(any(DeliveryCompany.class))).thenReturn(deliveryCompany);

        Long companyId = deliveryCompanyCommandService.createDeliveryCompany(deliveryCompanyCreateDto);

        assertNotNull(companyId);
        verify(deliveryCompanyRepo).save(any(DeliveryCompany.class));
    }

    @Test
    public void testUpdateDeliveryCompanyPhone() {
        deliveryCompany.setIdToTest(1L);
        when(deliveryCompanyRepo.findById(1L)).thenReturn(Optional.of(deliveryCompany));

        deliveryCompanyCommandService.updateDeliveryCompanyPhone(1L, "987654321");

        assertEquals("987654321", deliveryCompany.getPhone());
        verify(deliveryCompanyRepo).findById(1L);
    }

    @Test
    public void testDeleteDeliveryCompany() {
        deliveryCompany.setIdToTest(1L);
        when(deliveryCompanyRepo.findById(1L)).thenReturn(Optional.of(deliveryCompany));

        deliveryCompanyCommandService.deleteDeliveryCompany(1L);

        verify(deliveryCompanyRepo).deleteById(1L);
    }

}
