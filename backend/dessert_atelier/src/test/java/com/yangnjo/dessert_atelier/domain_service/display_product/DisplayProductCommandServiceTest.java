package com.yangnjo.dessert_atelier.domain_service.display_product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.display_product.DisplayProduct;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DisplayProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DisplayProductUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.impl.DpCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;

class DisplayProductCommandServiceTest {

    @InjectMocks
    private DpCommandServiceImpl displayProductCommandService;

    @Mock
    private DisplayProductRepository displayProductRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDisplayProduct() {
        DisplayProductCreateDto dto = new DisplayProductCreateDto("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        displayProduct.setIdToTest(1L);
        
        when(displayProductRepository.save(any(DisplayProduct.class))).thenReturn(displayProduct);
        when(displayProductRepository.existsByNaming("Test Product")).thenReturn(false);

        Long id = displayProductCommandService.createDisplayProduct(dto);

        assertNotNull(id);
        verify(displayProductRepository, times(1)).save(any(DisplayProduct.class));
    }

    @Test
    void createDisplayProduct_AlreadyExists() {
        DisplayProductCreateDto dto = new DisplayProductCreateDto("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        
        when(displayProductRepository.existsByNaming("Test Product")).thenReturn(true);

        assertThrows(DisplayProductAlreadyExistException.class, () -> {
            displayProductCommandService.createDisplayProduct(dto);
        });
    }

    @Test
    void updateDisplayProduct() {
        DisplayProductUpdateDto dto = new DisplayProductUpdateDto(1L, "Updated Product", "Updated Description", "updated_thumb.jpg");
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        displayProduct.setIdToTest(1L);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductRepository.existsByNaming("Updated Product")).thenReturn(false);

        displayProductCommandService.updateDisplayProduct(dto);

        assertEquals("Updated Product", displayProduct.getNaming());
        assertEquals("Updated Description", displayProduct.getDescription());
        assertEquals("updated_thumb.jpg", displayProduct.getThumb());
        verify(displayProductRepository, times(1)).save(displayProduct);
    }

    @Test
    void updateDisplayProduct_NotFound() {
        DisplayProductUpdateDto dto = new DisplayProductUpdateDto(1L, "Updated Product", "Updated Description", "updated_thumb.jpg");

        when(displayProductRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            displayProductCommandService.updateDisplayProduct(dto);
        });
    }

    @Test
    void updateDisplayProduct_NamingAlreadyExists() {
        DisplayProductUpdateDto dto = new DisplayProductUpdateDto(1L, "Updated Product", "Updated Description", "updated_thumb.jpg");
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        displayProduct.setIdToTest(1L);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductRepository.existsByNaming("Updated Product")).thenReturn(true);

        assertThrows(DisplayProductAlreadyExistException.class, () -> {
            displayProductCommandService.updateDisplayProduct(dto);
        });
    }

    @Test
    void updateDisplayProductSaleStatus() {
        Long dpId = 1L;
        SaleStatus saleStatus = SaleStatus.ON_SALE;
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.HIDE);
        displayProduct.setIdToTest(dpId);

        when(displayProductRepository.findById(dpId)).thenReturn(Optional.of(displayProduct));

        displayProductCommandService.updateDisplayProductSaleStatus(dpId, saleStatus);

        assertEquals(saleStatus, displayProduct.getSaleStatus());
        verify(displayProductRepository, times(1)).save(displayProduct);
    }

    @Test
    void updateDisplayProductSaleStatus_NotFound() {
        Long dpId = 1L;
        SaleStatus saleStatus = SaleStatus.ON_SALE;

        when(displayProductRepository.findById(dpId)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            displayProductCommandService.updateDisplayProductSaleStatus(dpId, saleStatus);
        });
    }

    @Test
    void deleteDisplayProduct() {
        Long dpId = 1L;

        displayProductCommandService.deleteDisplayProduct(dpId);

        verify(displayProductRepository, times(1)).deleteById(dpId);
    }
}
