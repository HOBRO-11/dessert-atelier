package com.yangnjo.dessert_atelier.domain_service.product;

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

import com.yangnjo.dessert_atelier.domain.product.Component;
import com.yangnjo.dessert_atelier.domain.product.ComponentUnit;
import com.yangnjo.dessert_atelier.domain.product.Product;
import com.yangnjo.dessert_atelier.domain.product.ProductStatus;
import com.yangnjo.dessert_atelier.domain.product.Recipe;
import com.yangnjo.dessert_atelier.domain_service.product.dto.RecipeCreateDto;
import com.yangnjo.dessert_atelier.domain_service.product.dto.RecipeCreateDto.ComponentQuantity;
import com.yangnjo.dessert_atelier.domain_service.product.impl.RecipeCommandServiceImpl;
import com.yangnjo.dessert_atelier.repository.ComponentRepository;
import com.yangnjo.dessert_atelier.repository.ProductRepository;
import com.yangnjo.dessert_atelier.repository.RecipeRepository;

class RecipeCommandServiceTest {

  @InjectMocks
  private RecipeCommandServiceImpl recipeCommandService;

  @Mock
  private RecipeRepository recipeRepository;

  @Mock
  private ComponentRepository componentRepository;

  @Mock
  private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRecipes_성공() {
    // Given
    Long productId = 1L;
    Long componentId1 = 1L;
    Long componentId2 = 2L;
    Product product = new Product("테스트 제품", 10000, "thumb.jpg", ProductStatus.AVAILABLE);
    Component component1 = new Component("설탕", ComponentUnit.GRAM);
    Component component2 = new Component("계란", ComponentUnit.COUNT);

    List<ComponentQuantity> componentQuantities = Arrays.asList(
        new ComponentQuantity(componentId1, 100),
        new ComponentQuantity(componentId2, 200));
    RecipeCreateDto dto = new RecipeCreateDto(productId, componentQuantities);

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(componentRepository.findById(componentId1)).thenReturn(Optional.of(component1));
    when(componentRepository.findById(componentId2)).thenReturn(Optional.of(component2));
    when(recipeRepository.saveAll(anyList())).thenAnswer(invocation -> {
      List<Recipe> recipes = invocation.getArgument(0);
      recipes.get(0).setIdToTest(3L);
      recipes.get(1).setIdToTest(4L);
      return recipes;
    });

    // When
    List<Long> result = recipeCommandService.createRecipes(dto);

    // Then
    assertEquals(Arrays.asList(3L, 4L), result);
    verify(productRepository).findById(productId);
    verify(componentRepository, times(2)).findById(anyLong());
    verify(recipeRepository).saveAll(anyList());
  }

  @Test
  void updateRecipeQuantity_성공() {
    // Given
    Long recipeId = 1L;
    Integer newQuantity = 150;
    Component component1 = new Component("설탕", ComponentUnit.GRAM);

    Recipe recipe = new Recipe(component1, 12);

    when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

    // When
    recipeCommandService.updateRecipeQuantity(recipeId, newQuantity);

    // Then
    assertEquals(newQuantity, recipe.getQuantity());
    verify(recipeRepository).findById(recipeId);
  }

  @Test
  void deleteRecipe_성공() {
    // Given
    Long recipeId = 1L;

    // When
    recipeCommandService.deleteRecipe(recipeId);

    // Then
    verify(recipeRepository).deleteById(recipeId);
  }
}