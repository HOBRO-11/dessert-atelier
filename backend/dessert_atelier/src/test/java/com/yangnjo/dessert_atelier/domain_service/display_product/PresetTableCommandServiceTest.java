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
import com.yangnjo.dessert_atelier.domain.display_product.PresetTable;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.PresetTableCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.PresetTableAlreadyException;
import com.yangnjo.dessert_atelier.domain_service.display_product.impl.PtCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.DisplayProductRepository;
import com.yangnjo.dessert_atelier.repository.PresetTableRepository;

class PresetTableCommandServiceTest {

    @InjectMocks
    private PtCommandServiceImpl presetTableCommandService;

    @Mock
    private PresetTableRepository presetTableRepository;

    @Mock
    private DisplayProductRepository displayProductRepository;

    @Mock
    private DisplayProductPresetRepository displayProductPresetRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPresetTable() {
        PresetTableCreateDto dto = new PresetTableCreateDto(1L, 1L);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset displayProductPreset = DisplayProductPreset.createDefaultDPP(displayProduct, null, null, null, 0, 0, null, null);
        PresetTable presetTable = new PresetTable(displayProduct, displayProductPreset);
        presetTable.setIdToTest(1L);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.of(displayProductPreset));
        when(presetTableRepository.save(any(PresetTable.class))).thenReturn(presetTable);
        when(presetTableRepository.existsByDisplayProduct(displayProduct)).thenReturn(false);

        Long id = presetTableCommandService.createPresetTable(dto);

        assertNotNull(id);
        verify(presetTableRepository, times(1)).save(any(PresetTable.class));
    }

    @Test
    void createPresetTable_DisplayProductNotFound() {
        PresetTableCreateDto dto = new PresetTableCreateDto(1L, 1L);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            presetTableCommandService.createPresetTable(dto);
        });
    }

    @Test
    void createPresetTable_DisplayProductPresetNotFound() {
        PresetTableCreateDto dto = new PresetTableCreateDto(1L, 1L);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            presetTableCommandService.createPresetTable(dto);
        });
    }

    @Test
    void createPresetTable_AlreadyExists() {
        PresetTableCreateDto dto = new PresetTableCreateDto(1L, 1L);
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset displayProductPreset = DisplayProductPreset.createDefaultDPP(displayProduct, null, null, null, 0, 0, null, null);

        when(displayProductRepository.findById(1L)).thenReturn(Optional.of(displayProduct));
        when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.of(displayProductPreset));
        when(presetTableRepository.existsByDisplayProduct(displayProduct)).thenReturn(true);

        assertThrows(PresetTableAlreadyException.class, () -> {
            presetTableCommandService.createPresetTable(dto);
        });
    }

    @Test
    void updatePresetTableNumbering() {
        Long presetTableId = 1L;
        Integer numbering = 2;
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset displayProductPreset = DisplayProductPreset.createDefaultDPP(displayProduct, null, null, null, 0, 0, null, null);
        PresetTable presetTable = new PresetTable(displayProduct, displayProductPreset);
        presetTable.setIdToTest(presetTableId);
        presetTableRepository.save(presetTable);

        when(presetTableRepository.findById(presetTableId)).thenReturn(Optional.of(presetTable));

        presetTableCommandService.updatePresetTableNumbering(presetTableId, numbering);

        assertEquals(numbering, presetTable.getNumbering());
        verify(presetTableRepository, times(1)).save(presetTable);
    }

    @Test
    void updatePresetTableNumbering_NotFound() {
        Long presetTableId = 1L;
        Integer numbering = 2;

        when(presetTableRepository.findById(presetTableId)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            presetTableCommandService.updatePresetTableNumbering(presetTableId, numbering);
        });
    }

    @Test
    void updatePresetTableDefault() {
        Long presetTableId = 1L;
        Long defaultDppId = 2L;
        DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset displayProductPreset = DisplayProductPreset.createDefaultDPP(displayProduct, null, null, null, 0, 0, null, null);
        PresetTable presetTable = new PresetTable(displayProduct, displayProductPreset);
        presetTable.setIdToTest(presetTableId);
        presetTableRepository.save(presetTable);

        when(presetTableRepository.findById(presetTableId)).thenReturn(Optional.of(presetTable));
        when(displayProductPresetRepository.findById(defaultDppId)).thenReturn(Optional.of(displayProductPreset));

        presetTableCommandService.updatePresetTableDefault(presetTableId, defaultDppId);

        assertEquals(displayProductPreset, presetTable.getDefaultDpp());
        verify(presetTableRepository, times(1)).save(presetTable);
    }

    @Test
    void updatePresetTableDefault_NotFound() {
        Long presetTableId = 1L;
        Long defaultDppId = 2L;

        when(presetTableRepository.findById(presetTableId)).thenReturn(Optional.empty());

        assertThrows(DisplayProductNotFountException.class, () -> {
            presetTableCommandService.updatePresetTableDefault(presetTableId, defaultDppId);
        });
    }

    @Test
    void deletePresetTable() {
        Long presetTableId = 1L;

        presetTableCommandService.deletePresetTable(presetTableId);

        verify(presetTableRepository, times(1)).deleteById(presetTableId);
    }
}
