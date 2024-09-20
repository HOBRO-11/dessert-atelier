package com.yangnjo.dessert_atelier.domain.service.postgreImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.common.dto.product.ProductDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductSaveDto;
import com.yangnjo.dessert_atelier.common.dto.product.ProductUpdateDto;
import com.yangnjo.dessert_atelier.db.entity.Products;
import com.yangnjo.dessert_atelier.db.model.ProductStatus;
import com.yangnjo.dessert_atelier.db.repository.ProductRepository;
import com.yangnjo.dessert_atelier.domain.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository; // JPA Repository for CRUD

    @Override
    public List<ProductDto> getProductsWithStatus(int page, int size, ProductStatus status) {
        return productRepository.findProductsWithStatus(page, size, status);
    }

    @Override
    public ProductDto saveProduct(ProductSaveDto dto) {
        Products product = dto.toEntity();
        Products savedProduct = productRepository.save(product);
        return ProductDto.toDto(savedProduct);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductDto.toDto(product);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductUpdateDto dto) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.modify(dto.getName(), dto.getPrice(), dto.getThumb(), dto.getStatus());

        Products updatedProduct = productRepository.save(product);
        return ProductDto.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
