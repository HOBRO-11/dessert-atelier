package com.yangnjo.dessert_atelier.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.dto.product.ProductDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductSaveDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductUpdateDto;
import com.yangnjo.dessert_atelier.db.entity.Products;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;
import com.yangnjo.dessert_atelier.db.repository.ProductRepository;

@SpringBootTest
@Transactional
@Rollback
public class ProductServiceImplTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void testSaveProduct() {
        // Given
        ProductSaveDto dto = new ProductSaveDto("Test Product", 100, ProductStatus.SALE, "thumb1");

        // When
        ProductDto savedProduct = productService.saveProduct(dto);

        // Then
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
        assertThat(savedProduct.getPrice()).isEqualTo(100);
        assertThat(savedProduct.getThumb()).isEqualTo("thumb1");
    }

    @Test
    void testGetProductById() {
        // Given
        Products product = Products.createProduct("Product 1", ProductStatus.SALE, 50, "thumb2");
        Products savedProduct = productRepository.save(product);

        // When
        ProductDto foundProduct = productService.getProductById(savedProduct.getId());

        // Then
        assertThat(foundProduct.getName()).isEqualTo("Product 1");
        assertThat(foundProduct.getPrice()).isEqualTo(50);
        assertThat(foundProduct.getThumb()).isEqualTo("thumb2");
    }

    @Test
    void testUpdateProduct() {
        // Given
        Products product = Products.createProduct("Old Product", ProductStatus.SALE, 200, "thumb3");
        Products savedProduct = productRepository.save(product);

        ProductUpdateDto dto = new ProductUpdateDto("Updated Product", 300, ProductStatus.SOLD_OUT, "thumb4");

        // When
        ProductDto updatedProduct = productService.updateProduct(savedProduct.getId(), dto);

        // Then
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(300);
        assertThat(updatedProduct.getThumb()).isEqualTo("thumb4");
    }

    @Test
    void testDeleteProduct() {
        // Given
        Products product = Products.createProduct("Product to Delete", ProductStatus.SALE, 150, "thumb5");
        Products savedProduct = productRepository.save(product);

        // When
        productService.deleteProduct(savedProduct.getId());

        // Then
        Optional<Products> deletedProduct = productRepository.findById(savedProduct.getId());
        assertThat(deletedProduct).isEmpty();
    }

    @Test
    void testGetProductsWithStatus() {
        // Given
        Products product1 = Products.createProduct("Product 1", ProductStatus.SALE, 100, "thumb6");
        Products product2 = Products.createProduct("Product 2", ProductStatus.SOLD_OUT, 200, "thumb7");
        productRepository.saveAll(Arrays.asList(product1, product2));

        // When
        List<ProductDto> products = productService.getProductsWithStatus(0, 10, ProductStatus.SALE);

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Product 1");
    }
}