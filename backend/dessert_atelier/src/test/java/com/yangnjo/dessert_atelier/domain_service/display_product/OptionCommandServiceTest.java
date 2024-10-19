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
import com.yangnjo.dessert_atelier.domain.display_product.Option;
import com.yangnjo.dessert_atelier.domain.display_product.OptionStatus;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.OptionCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.DisplayProductPresetNotFountException;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.display_product.impl.OptionCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.DisplayProductPresetRepository;
import com.yangnjo.dessert_atelier.repository.OptionRepository;

class OptionCommandServiceTest {

  @InjectMocks
  private OptionCommandServiceImpl optionCommandService;

  @Mock
  private OptionRepository optionRepository;

  @Mock
  private DisplayProductPresetRepository displayProductPresetRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createOption() {
    OptionCreateDto dto = new OptionCreateDto(1L, 10, "Test Option", 100, 1);
    DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
    DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(displayProduct, "Preset Name", "thumb.jpg",
        "Title", 1000, 1, "Content", null);
    Option option = new Option(dpp, 10, "Test Option", 100, 1);
    option.setIdToTest(1L);

    when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.of(dpp));
    when(optionRepository.save(any(Option.class))).thenReturn(option);

    Long id = optionCommandService.createOption(dto);

    assertNotNull(id);
    verify(optionRepository, times(1)).save(any(Option.class));
  }

  @Test
  void createOption_DisplayProductPresetNotFound() {
    OptionCreateDto dto = new OptionCreateDto(1L, 10, "Test Option", 100, 1);

    when(displayProductPresetRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(DisplayProductPresetNotFountException.class, () -> {
      optionCommandService.createOption(dto);
    });
  }

  @Test
  void updateOptionStatus() {
    Long optionId = 1L;
    DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
    DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(displayProduct, "Preset Name", "thumb.jpg",
        "Title", 1000, 1, "Content", null);
    OptionStatus status = OptionStatus.UNAVAILABLE;
    Option option = new Option(dpp, 10, "Test Option", 100, 1);
    option.setIdToTest(optionId);
    optionRepository.save(option);

    when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));

    optionCommandService.updateOptionStatus(optionId, status);

    assertEquals(status, option.getOptionStatus());
    verify(optionRepository, times(1)).save(option);
  }

  @Test
  void updateOptionStatus_NotFound() {
    Long optionId = 1L;
    OptionStatus status = OptionStatus.UNAVAILABLE;

    when(optionRepository.findById(optionId)).thenReturn(Optional.empty());

    assertThrows(OptionNotFoundException.class, () -> {
      optionCommandService.updateOptionStatus(optionId, status);
    });
  }

  @Test
  void updateOptionTotalQuantity() {
    Long optionId = 1L;
    Integer totalQuantity = 20;
    DisplayProduct displayProduct = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
    DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(displayProduct, "Preset Name", "thumb.jpg",
        "Title", 1000, 1, "Content", null);
    Option option = new Option(dpp, 10, "Test Option", 100, 1);
    option.setIdToTest(optionId);
    optionRepository.save(option);

    when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));

    optionCommandService.updateOptionTotalQuantity(optionId, totalQuantity);

    assertEquals(totalQuantity, option.getTotalQuantity());
    verify(optionRepository, times(1)).save(option);
  }

  @Test
  void updateOptionTotalQuantity_NotFound() {
    Long optionId = 1L;
    Integer totalQuantity = 20;

    when(optionRepository.findById(optionId)).thenReturn(Optional.empty());

    assertThrows(OptionNotFoundException.class, () -> {
      optionCommandService.updateOptionTotalQuantity(optionId, totalQuantity);
    });
  }

  @Test
  void deleteOption() {
    Long optionId = 1L;

    optionCommandService.deleteOption(optionId);

    verify(optionRepository, times(1)).deleteById(optionId);
  }
}
