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
import com.yangnjo.dessert_atelier.domain.display_product.ProductQuantity;
import com.yangnjo.dessert_atelier.domain.display_product.SaleStatus;
import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain_service.display_product.dto.ProductQuantityCreateDto;
import com.yangnjo.dessert_atelier.domain_service.display_product.exception.OptionNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.display_product.impl.PqCommandServiceImpl;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductNotFoundException;
import com.yangnjo.dessert_atelier.repository.OptionRepository;
import com.yangnjo.dessert_atelier.repository.ProductQuantityRepository;
import com.yangnjo.dessert_atelier.repository.ProductRepository;

class ProductQuantityCommandServiceTest {

    @InjectMocks
    private PqCommandServiceImpl productQuantityCommandService;

    @Mock
    private ProductQuantityRepository productQuantityRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OptionRepository optionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProductQuantity() {
        ProductQuantityCreateDto dto = new ProductQuantityCreateDto(1L, 1L, 10);
        Product product = new Product("Test Product", 100, "thumb.jpg", ProductStatus.AVAILABLE);
        DisplayProduct dp = new DisplayProduct("Test Product", "Description", "thumb.jpg", SaleStatus.ON_SALE);
        DisplayProductPreset dpp = DisplayProductPreset.createDefaultDPP(dp, "tset Preset Name", "thumb.jpg", "Title", 1000, 1, "Content", null);
        Option option = new Option(dpp, 10, "Test Option", 100, 1);
        ProductQuantity productQuantity = new ProductQuantity(product, option, 10);
        productQuantity.setIdToTest(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.findById(1L)).thenReturn(Optional.of(option));
        when(productQuantityRepository.save(any(ProductQuantity.class))).thenReturn(productQuantity);

        Long id = productQuantityCommandService.createProductQuantity(dto);

        assertNotNull(id);
        verify(productQuantityRepository, times(1)).save(any(ProductQuantity.class));
    }

    @Test
    void createProductQuantity_ProductNotFound() {
        ProductQuantityCreateDto dto = new ProductQuantityCreateDto(1L, 1L, 10);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productQuantityCommandService.createProductQuantity(dto);
        });
    }

    @Test
    void createProductQuantity_OptionNotFound() {
        ProductQuantityCreateDto dto = new ProductQuantityCreateDto(1L, 1L, 10);
        Product product = new Product("Test Product", 100, "thumb.jpg", ProductStatus.AVAILABLE);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OptionNotFoundException.class, () -> {
            productQuantityCommandService.createProductQuantity(dto);
        });
    }

    @Test
    void deleteProductQuantity() {
        Long productQuantityId = 1L;

        productQuantityCommandService.deleteProductQuantity(productQuantityId);

        verify(productQuantityRepository, times(1)).deleteById(productQuantityId);
    }
}