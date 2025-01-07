package com.yangnjo.dessert_atelier.service.product.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductCreateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DisplayProductEntityCreateForm {
    String title;
    List<String> thumb;
    List<String> desc;
    List<MultipartFile> thumbImages;
    List<MultipartFile> descImages;

    public DisplayProductCreateDto toDto(List<String> thumb, List<String> desc) {
        return new DisplayProductCreateDto(title, thumb, desc);
    }
}
