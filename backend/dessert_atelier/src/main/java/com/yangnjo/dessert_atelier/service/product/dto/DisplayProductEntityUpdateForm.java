package com.yangnjo.dessert_atelier.service.product.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yangnjo.dessert_atelier.domain_service.product.dto.DisplayProductUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class DisplayProductEntityUpdateForm {
    @Setter
    Long dpId;
    List<String> thumb;
    List<String> desc;
    Integer optionLayer;
    List<MultipartFile> images;

    /**
     * 변경하길 원하는 파라미터만 설정, 나머지는 반드시 null 입력
     */
    public DisplayProductUpdateDto toDto(List<String> thumb, List<String> desc, Integer optionLayer) {
        return new DisplayProductUpdateDto(dpId, thumb, desc, optionLayer);
    }
}
