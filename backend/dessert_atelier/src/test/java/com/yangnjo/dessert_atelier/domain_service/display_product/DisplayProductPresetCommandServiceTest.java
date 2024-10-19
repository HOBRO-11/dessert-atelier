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
import com.yangnjo.dessert_atelier.domain.display_product.DisplayProductPreset;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DppCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.DppUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductPresetNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DppAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.display_product.impl.DppCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;

class DisplayProductPresetCommandServiceTest {

    @InjectMocks
    private DppCommandServiceImpl displayProductPresetCommandService;

    @Mock
    private DisplayProductPresetRepository displayProductPresetRepository;

    @Mock
    private DisplayProductRepository displayProductRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDefaultDPP() {
        DppCreateDto dto = new DppCreateDto(1L, "Preset Name", "thumb.jpg", "Title", 1000, 1, "Content", null);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset preset = DisplayProductPreset.createDefaultDPP(displayProduct, "Preset Name", "thumb.jpg", "Title", 1000, 1, "Content", null);
        preset.setIdToTest(1L);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductPresetRepository.save(any(DisplayProductPreset.class))).thenReturn(preset);
        when(displayProductPresetRepository.existsByDisplayProductIdAndNaming(1L, "Preset Name")).thenReturn(false);

        Long id = displayProductPresetCommandService.createDefaultDPP(dto);

        assertNotNull(id);
        verify(displayProductPresetRepository, times(1)).save(any(DisplayProductPreset.class));
    }

    @Test
    void createDefaultDPP_DisplayProductNotFound() {
        DppCreateDto dto = new DppCreateDto(1L, "Preset Name", "thumb.jpg", "Title", 1000, 1, "Content", null);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            displayProductPresetCommandService.createDefaultDPP(dto);
        });
    }

    @Test
    void createDefaultDPP_AlreadyExists() {
        DppCreateDto dto = new DppCreateDto(1L, "Preset Name", "thumb.jpg", "Title", 1000, 1, "Content", null);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductPresetRepository.existsByDisplayProductIdAndNaming(1L, "Preset Name")).thenReturn(true);

        assertThrows(DppAlreadyExistException.class, () -> {
            displayProductPresetCommandService.createDefaultDPP(dto);
        });
    }

    @Test
    void updateDPP() {
        DppUpdateDto dto = new DppUpdateDto(1L, "Updated Preset Name", "Updated Content", 10);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset preset = DisplayProductPreset.createDefaultDPP(displayProduct, "Preset Name", "thumb.jpg", "Title", 1000, 1, "Content", null);
        displayProductPresetRepository.save(preset);
        preset.setIdToTest(1L);
        preset.setContent("Old Content");

        when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.of(preset));

        displayProductPresetCommandService.updateDPP(dto);

        assertEquals("Updated Preset Name", preset.getNaming());
        assertEquals(10, preset.getPercentDiscount());
        verify(displayProductPresetRepository, times(1)).save(preset);
    }

    @Test
    void updateDPP_NotFound() {
        DppUpdateDto dto = new DppUpdateDto(1L, "Updated Preset Name", "Updated Content", 10);

        when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisplayProductPresetNotFountException.class, () -> {
            displayProductPresetCommandService.updateDPP(dto);
        });
    }

    @Test
    void deleteDisplayProductPreset() {
        Long dppId = 1L;

        displayProductPresetCommandService.deleteDisplayProductPreset(dppId);

        verify(displayProductPresetRepository, times(1)).deleteById(dppId);
    }
}
