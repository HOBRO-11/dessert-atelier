package com.yangnjo.dessert_atelier.domain_service.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ProductUpdateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ProductAlreadyExistException;
import com.yangnjo.dessert_atelier.domain_service.product.impl.ProductCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.ProductRepository;

class ProductCommandServiceTest {

    @InjectMocks
    private ProductCommandServiceImpl productCommandService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_성공() {
        // Given
        ProductCreateDto dto = new ProductCreateDto("테스트 제품", 10000, "thumb.jpg", ProductStatus.AVAILABLE);
        Product product = dto.toEntity();
        product.setIdToTest(1L);

        when(productRepository.existsByName(dto.getName())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Long result = productCommandService.createProduct(dto);

        // Then
        assertEquals(1L, result);
        verify(productRepository).existsByName(dto.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_이미존재하는제품명() {
        // Given
        ProductCreateDto dto = new ProductCreateDto("이미 존재하는 제품", 10000, "thumb.jpg", ProductStatus.AVAILABLE);
        when(productRepository.existsByName(dto.getName())).thenReturn(true);

        // When & Then
        assertThrows(ProductAlreadyExistException.class, () -> productCommandService.createProduct(dto));
        verify(productRepository).existsByName(dto.getName());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_성공() {
        // Given
        Long productId = 1L;
        Product existingProduct = new Product("기존 제품", 10000, "old_thumb.jpg", ProductStatus.AVAILABLE);
        existingProduct.setIdToTest(productId);

        ProductUpdateDto dto = new ProductUpdateDto(productId, "수정된 제품", 12000, "new_thumb.jpg");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // When
        productCommandService.updateProduct(dto);

        // Then
        assertEquals("수정된 제품", existingProduct.getName());
        assertEquals(12000, existingProduct.getPrice());
        assertEquals("new_thumb.jpg", existingProduct.getThumb());
        verify(productRepository).findById(productId);
    }

    @Test
    void updateProductStatus_성공() {
        // Given
        Long productId = 1L;
        Product existingProduct = new Product("테스트 제품", 10000, "thumb.jpg", ProductStatus.AVAILABLE);
        existingProduct.setIdToTest(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // When
        productCommandService.updateProductStatus(productId, ProductStatus.UNAVAILABLE);

        // Then
        assertEquals(ProductStatus.UNAVAILABLE, existingProduct.getProductStatus());
        verify(productRepository).findById(productId);
    }

    @Test
    void deleteProduct_성공() {
        // Given
        Long productId = 1L;

        // When
        productCommandService.deleteProduct(productId);

        // Then
        verify(productRepository).deleteById(productId);
    }
}