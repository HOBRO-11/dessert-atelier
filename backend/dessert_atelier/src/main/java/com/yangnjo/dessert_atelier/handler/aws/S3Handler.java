package com.yangnjo.dessert_atelier.handler.aws;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.yangnjo.dessert_atelier.handler.aws.dto.UploadFileRequest;
import com.yangnjo.dessert_atelier.handler.aws.exception.UploadFileException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Handler {

    private final AmazonS3Client s3Client;

    public void uploadFileParallel(List<UploadFileRequest> uploadFileRequests) {
        ExecutorService executorService = Executors.newFixedThreadPool(uploadFileRequests.size());
        CountDownLatch countDownLatch = new CountDownLatch(uploadFileRequests.size());
        Map<Integer, Throwable> concurrentHashMap = new ConcurrentHashMap<>();

        for (UploadFileRequest uploadFileRequest : uploadFileRequests) {
            executorService.submit(() -> {
                try {
                    s3Client.putObject(uploadFileRequest.getPutObjectRequest());
                } catch (Exception e) {
                    e.printStackTrace();
                    concurrentHashMap.put(uploadFileRequests.indexOf(uploadFileRequest), e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new UploadFileException(e);
        }

        if (concurrentHashMap.isEmpty() == false) {
            Throwable e = concurrentHashMap.entrySet().iterator().next().getValue();
            throw new UploadFileException(e);
        }

    }

    public void uploadFile(UploadFileRequest uploadFileRequest) {
        try {
            s3Client.putObject(uploadFileRequest.getPutObjectRequest());
        } catch (Exception e) {
            throw new UploadFileException(e);
        }
    }

}
