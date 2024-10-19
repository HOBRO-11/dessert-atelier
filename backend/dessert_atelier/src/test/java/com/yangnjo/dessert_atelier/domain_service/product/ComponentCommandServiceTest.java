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

import com.yangnjo.dessert_atelier.domain.product.Component;
import com.yangnjo.dessert_atelier.domain.product.ComponentUnit;
import com.yangnjo.dessert_atelier.domain_service.product.dto.ComponentCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.exception.ComponentNotFoundException;
import com.yangnjo.dessert_atelier.domain_service.product.impl.ComponentCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.ComponentRepository;

class ComponentCommandServiceTest {

    @InjectMocks
    private ComponentCommandServiceImpl componentCommandService;

    @Mock
    private ComponentRepository componentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComponent_성공() {
        // Given
        ComponentCreateDto dto = new ComponentCreateDto("설탕", ComponentUnit.GRAM);
        Component component = dto.toEntity();
        component.setIdToTest(1L);
        
        when(componentRepository.save(any(Component.class))).thenReturn(component);

        // When
        Long result = componentCommandService.createComponent(dto);

        // Then
        assertEquals(1L, result);
        verify(componentRepository).save(any(Component.class));
    }

    @Test
    void updateComponentName_성공() {
        // Given
        Long componentId = 1L;
        String newName = "갈색설탕";
        Component component = new Component("설탕", ComponentUnit.GRAM);
        component.setIdToTest(componentId);
        
        when(componentRepository.findById(componentId)).thenReturn(Optional.of(component));

        // When
        componentCommandService.updateComponentName(componentId, newName);

        // Then
        assertEquals(newName, component.getName());
        verify(componentRepository).findById(componentId);
    }

    @Test
    void updateComponentName_컴포넌트없음_예외발생() {
        // Given
        Long componentId = 1L;
        String newName = "갈색설탕";
        
        when(componentRepository.findById(componentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ComponentNotFoundException.class, () -> {
            componentCommandService.updateComponentName(componentId, newName);
        });
        verify(componentRepository).findById(componentId);
    }

    @Test
    void deleteComponent_성공() {
        // Given
        Long componentId = 1L;

        // When
        componentCommandService.deleteComponent(componentId);

        // Then
        verify(componentRepository).deleteById(componentId);
    }
}