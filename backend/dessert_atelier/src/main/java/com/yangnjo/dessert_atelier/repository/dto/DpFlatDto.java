package com.yangnjo.dessert_atelier.repository.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.DisplayProductStatus;
import com.yangnjo.dessert_atelier.domain.SalePolicyStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DpFlatDto {

    private Long id;

    private String title;

    private int price;

    private String thumb;
    
    private String description;

    private SalePolicyStatus salePolicyStatus;

    private DisplayProductStatus displayProductStatus;

    private List<String> imageUrls;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
}
