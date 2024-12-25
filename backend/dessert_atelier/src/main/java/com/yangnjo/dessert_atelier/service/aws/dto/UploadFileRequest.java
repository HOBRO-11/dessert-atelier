package com.yangnjo.dessert_atelier.service.aws.dto;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadFileRequest {
    private String bucketName;
    private String filePath;
    private String filename;
    private MultipartFile file;

    public PutObjectRequest getPutObjectRequest() throws IOException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath + filename, file.getInputStream(), getObjectMetadata());
        return putObjectRequest;
    }

    private ObjectMetadata getObjectMetadata() {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }
}
