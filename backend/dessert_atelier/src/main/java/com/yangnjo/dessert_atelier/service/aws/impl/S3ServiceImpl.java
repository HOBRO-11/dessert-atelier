package com.yangnjo.dessert_atelier.service.aws.impl;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.yangnjo.dessert_atelier.service.aws.S3Service;
import com.yangnjo.dessert_atelier.service.aws.dto.UploadFileRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3Client s3Client;

    @Override
    public boolean uploadFileParallel(List<UploadFileRequest> uploadFileRequests) {
        ExecutorService executorService = Executors.newFixedThreadPool(uploadFileRequests.size());
        CountDownLatch countDownLatch = new CountDownLatch(uploadFileRequests.size());
        AtomicBoolean isSuccess = new AtomicBoolean(true);

        for (UploadFileRequest uploadFileRequest : uploadFileRequests) {
            executorService.submit(() -> {
                try {
                    s3Client.putObject(uploadFileRequest.getPutObjectRequest());
                } catch (Exception e) {
                    e.printStackTrace();
                    isSuccess.compareAndSet(true, false);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            isSuccess.compareAndSet(true, false);
        }

        return isSuccess.get();
    }

    @Override
    public boolean uploadFile(UploadFileRequest uploadFileRequest) {
        try {
            s3Client.putObject(uploadFileRequest.getPutObjectRequest());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
