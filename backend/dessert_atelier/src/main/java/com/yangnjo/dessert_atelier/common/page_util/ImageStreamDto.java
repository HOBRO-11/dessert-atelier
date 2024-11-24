package com.yangnjo.dessert_atelier.common.page_util;

import java.io.InputStream;
import java.util.List;

import com.yangnjo.dessert_atelier.common.web_util.ImageType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageStreamDto {
    private String name;
    private String path;
    private ImageType type;
    private Long streamSize;
    private boolean isDeleteOrigin;
    private List<ImageSizeOption> sizeOption;
    private InputStream inputStream;
}
