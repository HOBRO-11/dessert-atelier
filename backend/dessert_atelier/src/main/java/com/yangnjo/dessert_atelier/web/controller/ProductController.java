package com.yangnjo.dessert_atelier.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.repository.product.dto.ProductDto;
import com.yangnjo.dessert_atelier.service.product.ProductService;
import com.yangnjo.dessert_atelier.service.product.dto.ProductCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.ProductUpdateForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> create(ProductCreateForm form) {
        Long prodId = productService.create(form);
        return new ResponseEntity<>(prodId, HttpStatus.CREATED);
    }

    @GetMapping("/{prodId}")
    public ResponseEntity<ProductDto> get(@PathVariable Long prodId) {
        if (prodId < 1) {
            throw new IllegalArgumentException("prodId를 입력해주세요.");
        }

        Optional<ProductDto> optionalProd = productService.getByProductId(prodId);

        if (optionalProd.isPresent()) {
            return new ResponseEntity<>(optionalProd.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(@ModelAttribute PageOption pageOption) {
        List<ProductDto> list = productService.get(pageOption);

        if(list.isEmpty() == false) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("thumb/{prodId}")
    public ResponseEntity<Void> updateThumb(@PathVariable Long prodId, ProductUpdateForm form) {
        form.setProductId(prodId);
        productService.updateThumb(form);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("price/{prodId}")
    public ResponseEntity<Void> updatePrice(@PathVariable Long prodId, ProductUpdateForm form) {
        form.setProductId(prodId);
        productService.updatePrice(form);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("quantity/{prodId}")
    public ResponseEntity<Void> setQuantity(@PathVariable Long prodId, @RequestParam int quantity) {
        productService.setQuantity(prodId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
