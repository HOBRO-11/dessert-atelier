package com.yangnjo.dessert_atelier.common.web_util;

import com.amazonaws.services.s3.internal.InputSubstream;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public record ImgStreamProperties(String name, ImageType type, long length, InputSubstream inputSubstream) {
    
    /**
     * @param bucketName
     * @param path 이미지 저장을 할 디렉토리로 /로 끝나는 경로
     * @return
     */
    public PutObjectRequest getPutObjectRequest(String bucketName, String path){
        return new PutObjectRequest(bucketName, path + name, inputSubstream, getMetadata());
    }

    private ObjectMetadata getMetadata() {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(length);
        metadata.setContentType(type.getType());

        return metadata;
    }
}
