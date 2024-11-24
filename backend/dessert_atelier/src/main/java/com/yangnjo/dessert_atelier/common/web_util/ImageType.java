package com.yangnjo.dessert_atelier.common.web_util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {

    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    HEIF("image/heif");

    private final String type;
}
