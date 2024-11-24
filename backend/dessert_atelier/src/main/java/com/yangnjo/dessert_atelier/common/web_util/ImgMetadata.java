package com.yangnjo.dessert_atelier.common.web_util;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ImgMetadata(String originName, @JsonIgnore String newName, String type, long size) {

    @JsonCreator
    public ImgMetadata(@JsonProperty("name") String name, @JsonProperty("type") String type,
            @JsonProperty("size") long size) {
        this(name, UUID.randomUUID().toString(), type, size);
    }

}
