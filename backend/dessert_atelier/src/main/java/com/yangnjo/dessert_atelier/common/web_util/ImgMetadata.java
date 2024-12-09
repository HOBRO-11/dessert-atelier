package com.yangnjo.dessert_atelier.common.web_util;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ImgMetadata {
    private String originName;
    @JsonIgnore
    private String newName;
    private String type;
    private long size;

    @JsonCreator
    public ImgMetadata(@JsonProperty("name") String name, @JsonProperty("type") String type,
            @JsonProperty("size") long size) {
        this.originName = name;
        this.type = type;
        this.size = size;
        this.newName = createUUIDName(type);
    }

    private String createUUIDName(String type) {
        return UUID.randomUUID().toString() + "." + type;
    }

}
